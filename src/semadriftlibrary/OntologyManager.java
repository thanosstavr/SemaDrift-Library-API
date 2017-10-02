/*
 *    Copyright 2016 CERTH-ITI (http://certh.gr, http://iti.gr)
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package semadriftlibrary;

import semadriftlibrary.Constructors.OntProperty;
import semadriftlibrary.Constructors.OntClass;
import semadriftlibrary.Constructors.Concept;
import semadriftlibrary.Exceptions.OntologyCreationException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import semadriftlibrary.Constructors.ClassNameSorter;

/**
 *
 * @author andreadisst
 */
public class OntologyManager {
    
    private OWLOntology ontology;
    private final List<Concept> concepts;
    private ArrayList<OntProperty> properties;
    private java.util.List<java.util.Map.Entry<String,String>> individuals;
    private final ArrayList<OntClass> tree;
    private final OWLDataFactory factory;
    private String name;
    
    public OntologyManager(){
        concepts = new ArrayList<>();
        properties = new ArrayList<>();
        factory = OWLManager.getOWLDataFactory();
        tree = new ArrayList<>();
    }
    
    public void loadOntology(OWLOntology ontology){
        this.ontology = ontology;
        String iri = ontology.getOntologyID().getOntologyIRI().toString();
        name = iri.substring(iri.lastIndexOf("/")+1);
        extractInformation();
    }
    
    public void loadURL(String URL) throws OntologyCreationException{
        try{
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();       
            config = config.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
            IRI iri = IRI.create(URL);
            OWLOntologyDocumentSource documentSource = new IRIDocumentSource(iri);
            ontology = manager.loadOntologyFromOntologyDocument(documentSource, config);
            name = (iri.toString().contains("/")) ? iri.toString().substring(iri.toString().lastIndexOf("/")+1) : iri.toString();
            extractInformation();
        }catch(OWLOntologyCreationException ex){
            throw new OntologyCreationException(ex);
        }
    }
    
    public void loadFile(String path) throws OntologyCreationException{
        try{
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();       
            config = config.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT); 
            File file = new File(path);
            OWLOntologyDocumentSource documentSource = new FileDocumentSource(file);
            ontology = manager.loadOntologyFromOntologyDocument(documentSource, config);
            name = (path.contains("\\")) ? path.substring(path.lastIndexOf("\\")+1) : path;
            extractInformation();
        }catch(OWLOntologyCreationException ex){
            throw new OntologyCreationException(ex);
        }
        
    }
    
    public void extractInformation(){
        //Set<OWLClass> classes = ontology.getClassesInSignature();
        List<OWLClass> classes = new ArrayList<>();
        classes.addAll(ontology.getClassesInSignature());
        Collections.sort(classes,new ClassNameSorter());
        properties = extractProperties();
        individuals = extractIndividuals();
        
        //TODO This is not optimized
        for(OWLClass cls : classes){
            String cl = getShortForm(cls.getIRI().toString());
            //if(cl.equals("ComputerBased") || cl.equals("SoftwareBased") || cl.equals("MixedMedia")){ //TODO: remove in final version
                ArrayList<String> labels;
                ArrayList<OntProperty> tripletsAsDomain = new ArrayList<>();
                ArrayList<OntProperty> tripletsAsRange = new ArrayList<>();
                ArrayList<String> instances = new ArrayList<>();

                labels = extractLabels(cls);

                for(OntProperty tri : properties){
                    if(tri.getDomain().equals(cl)){
                        tripletsAsDomain.add(tri);
                    }
                    if(tri.getRange().equals(cl)){
                        tripletsAsRange.add(tri);
                    }
                }

                for(Map.Entry<String,String> individual : individuals){
                    if(individual.getValue().equals(cls.toString())){
                        instances.add(getShortForm(individual.getKey()));
                    }
                }

                concepts.add(new Concept(cls.toString(), cl, labels, tripletsAsDomain, tripletsAsRange, instances));
                
                if(cls.getSuperClasses(ontology).isEmpty())
                    tree.add(getLeaves(cls));
            //}
        }
    }
    
    private OntClass getLeaves(OWLClass cls){
        String cl = getShortForm(cls.getIRI().toString());
        ArrayList<OntClass> subclasses = new ArrayList<>();
        
        ArrayList<OWLClass> owlSubclasses = extractSubclasses(cls);
        for(OWLClass subcls : owlSubclasses){
            subclasses.add(getLeaves(subcls));
        }
        
        ArrayList<OntProperty> allProperties = new ArrayList<>();
        for(OntProperty property : properties){
            if(property.getDomain().equals(cl) || property.getRange().equals(cl)){
                allProperties.add(property);
            }
        }
        
        return new OntClass(cls.toString(), cl, subclasses, allProperties);
    }
    
    private ArrayList<String> extractLabels(OWLClass cls){
        ArrayList<String> labels = new ArrayList<>();
        //owlapi 4.1 : EntitySearcher.getAnnotations(cls, ontology, factory.getRDFSLabel()
        for(OWLAnnotation a : cls.getAnnotations(ontology, factory.getRDFSLabel())) {
            OWLAnnotationValue val = a.getValue();
            if(val instanceof OWLLiteral) {
                labels.add(((OWLLiteral) val).getLiteral());
            }
        }
        return labels;
    }
    
    private ArrayList<OWLClass> extractSubclasses(OWLClass cls){
        ArrayList<OWLClass> owlSubclasses = new ArrayList<>();
        Set<OWLClassExpression> expressions = cls.getSubClasses(ontology);
        for(OWLClassExpression expr : expressions){
            owlSubclasses.add(expr.asOWLClass());
        }
        return owlSubclasses;
    }
    
    private ArrayList<OntProperty> extractProperties(){
        ArrayList<OntProperty> properties = new ArrayList<>();
        
        ArrayList<OntProperty> op = extractObjectProperties();
        properties.addAll(op);
        
        ArrayList<OntProperty> dp = extractDataProperties();
        properties.addAll(dp);
        
        return properties;
    }
    
    private ArrayList<OntProperty> extractObjectProperties(){
        boolean isDatatype = false;
        
        ArrayList<OntProperty> objectProperties = new ArrayList<>();
        
        java.util.List<java.util.Map.Entry<String,String>> domainList= new java.util.ArrayList<>();
        java.util.List<java.util.Map.Entry<String,String>> rangeList= new java.util.ArrayList<>();
        
        for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLClass domain : op.getDomain().getClassesInSignature()){
                domainList.add(new java.util.AbstractMap.SimpleEntry<>(op.getProperty().toString(),getShortForm(domain.getIRI().toString())));
            }
        }
        for (OWLObjectPropertyRangeAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) { 
            for (OWLClass range : op.getRange().getClassesInSignature()){
                rangeList.add(new java.util.AbstractMap.SimpleEntry<>(op.getProperty().toString(),getShortForm(range.getIRI().toString())));
            }
        }
        
        for (Map.Entry<String, String> domain : domainList){
            for (Map.Entry<String, String> range : rangeList){
                if( domain.getKey().equals(range.getKey()) ){
                    objectProperties.add(new OntProperty(getShortForm(domain.getKey()), domain.getValue(), range.getValue(), isDatatype));
                }
            }
        }
        
        return objectProperties;
    }
    
    private ArrayList<OntProperty> extractDataProperties(){
        boolean isDatatype = true;
        
        ArrayList<OntProperty> dataProperties = new ArrayList<>();
        
        java.util.List<java.util.Map.Entry<String,String>> domainList= new java.util.ArrayList<>();
        java.util.List<java.util.Map.Entry<String,String>> rangeList= new java.util.ArrayList<>();
        
        for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLClass domain : dp.getDomain().getClassesInSignature()){
                domainList.add(new java.util.AbstractMap.SimpleEntry<>(dp.getProperty().toString(),getShortForm(domain.getIRI().toString())));
            }
        }
        
        for (OWLDataPropertyRangeAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) { 
            for (OWLDatatype range : dp.getRange().getDatatypesInSignature()){
                rangeList.add(new java.util.AbstractMap.SimpleEntry<>(dp.getProperty().toString(),range.toString()));
            }
        }
        
        for (Map.Entry<String, String> domain : domainList){
            for (Map.Entry<String, String> range : rangeList){
                if( domain.getKey().equals(range.getKey()) ){
                    dataProperties.add(new OntProperty(getShortForm(domain.getKey()), domain.getValue(), range.getValue(), isDatatype));
                }
            }
        }
        
        return dataProperties;
    }
    
    private java.util.List<java.util.Map.Entry<String,String>> extractIndividuals(){
        java.util.List<java.util.Map.Entry<String,String>> instances= new java.util.ArrayList<>();
        
        Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();
        for(OWLNamedIndividual individual : individuals){
            //owlapi 4.1 : EntitySearcher.getTypes(individual, ontology)
            Collection<OWLClassExpression> classes = individual.getTypes(ontology);
            for(OWLClassExpression cl : classes){
                instances.add(new java.util.AbstractMap.SimpleEntry<>(individual.toString(),cl.toString()));
            }
        }
        
        return instances;
    }
    
    public List<Concept> getConcepts(){
        return concepts;
    }
    
    public String getName(){
        return name;
    }
    
    public ArrayList<OntClass> getTree(){
        return tree;
    }
    
    private String getShortForm(String iri){
        if(iri.contains("#")){
            iri = iri.substring(iri.lastIndexOf("#")+1);
        }
        iri = iri.replace(">", "");
        return iri;
    }
}
