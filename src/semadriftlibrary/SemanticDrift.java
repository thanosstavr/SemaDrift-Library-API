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

import java.io.File;
import java.io.FileNotFoundException;
import semadriftlibrary.Constants.Constants;
import semadriftlibrary.Constructors.VersionPairs;
import semadriftlibrary.Constructors.Version;
import semadriftlibrary.Constructors.RankedConcept;
import semadriftlibrary.Constructors.Link;
import semadriftlibrary.Constructors.ConceptPair;
import semadriftlibrary.Constructors.Chain;
import semadriftlibrary.Constructors.AverageDrift;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.semanticweb.owlapi.model.OWLOntology;
import semadriftlibrary.Exceptions.OntologyCreationException;

/**
 * The SemanticDrift program implements an application that loads a set of
 * ontologies (possibly versions of the same ontology) and calculates the drift
 * between their concepts, according to label, intension, extension and whole
 * approach.
 * 
* @author PERICLES
 * @version 1.0
 * @since 2016-06-08
 */
public class SemanticDrift {

    private ArrayList<Version> versions;
    private final Calculations calc;

    /**
     * Constructor
     */
    public SemanticDrift() {
        versions = new ArrayList<>();
        calc = new Calculations();
    }

    //******************************************************
    //* Loading ontologies
    //******************************************************/
    /**
     * This method adds a new ontology (instead of an ontology file).
     *
     * @param ontology This is an ontology
     */
    public void addOntology(OWLOntology ontology) {
        OntologyManager manager = new OntologyManager();
        manager.loadOntology(ontology);
        versions.add(new Version(manager));
    }

    /**
     * This method adds a new ontology by file.
     *
     * @param path This is an ontology file path
     */
    public void addVersionFile(String path) throws OntologyCreationException, FileNotFoundException {

        //error handling
        if (path.isEmpty()) {
            //not initialized
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("File \"" + path + "\" does not exist");
        }

        //body
        OntologyManager manager = new OntologyManager();
        manager.loadFile(path);
        versions.add(new Version(manager));

    }

    /**
     * This method adds a list of ontology files.
     *
     * @param paths This is an arrayList of ontology file paths
     */
    public void addVersionFiles(ArrayList<String> paths) throws OntologyCreationException, FileNotFoundException {
        for (String path : paths) {
            addVersionFile(path);
        }
    }

    /**
     * This method adds a new ontology by URL.
     *
     * @param url This is an ontology URL
     */
    public void addVersionURL(String url) throws OntologyCreationException {
        OntologyManager manager = new OntologyManager();
        manager.loadURL(url);
        versions.add(new Version(manager));
    }

    /**
     * This method adds a list of ontology URLs.
     *
     * @param urls This is an arrayList of ontology URLs
     */
    public void addVersionURLs(ArrayList<String> urls) throws OntologyCreationException {
        for (String url : urls) {
            addVersionURL(url);
        }
    }

    /**
     * This method deletes the set of ontologies (clears the drift workplace).
     */
    public void clear() {
        versions = new ArrayList<>();
    }

    /**
     * @return This method returns the set of loaded ontologies.
     */
    public ArrayList<Version> getVersions() {
        return versions;
    }

    //******************************************************
    //* Average Drift
    //******************************************************/
    /**
     * This method calculates the average stability value between each two
     * consecutive versions per all aspects, based on hybrid approach.
     *
     * @return This returns an arrayList of AverageDrift instances that include
     * names of versions and stability values per aspect.
     */
    public ArrayList<AverageDrift> getAverageDriftChain() {

        ArrayList<AverageDrift> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            Version from = versions.get(i);
            Version to = versions.get(i + 1);
            String label = Constants.formatter.format(calc.getAVGLabelChain(versions.get(i), versions.get(i + 1)));
            String intension = Constants.formatter.format(calc.getAVGIntensionChain(versions.get(i), versions.get(i + 1)));
            String extension = Constants.formatter.format(calc.getAVGExtensionChain(versions.get(i), versions.get(i + 1)));
            String whole = Constants.formatter.format(calc.getAVGWholeChain(versions.get(i), versions.get(i + 1)));

            results.add(new AverageDrift(from, to, label, intension, extension, whole));
        }

        return results;
    }

    /**
     * This method calculates the average stability value between each two
     * consecutive versions, per all aspects.
     *
     * @return This returns an arrayList of AverageDrift instances that include
     * names of versions and stability values per aspect.
     */
    public ArrayList<AverageDrift> getAverageDrift() {

        ArrayList<AverageDrift> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            Version from = versions.get(i);
            Version to = versions.get(i + 1);
            String label = Constants.formatter.format(calc.getAVGLabel(versions.get(i), versions.get(i + 1)));
            String intension = Constants.formatter.format(calc.getAVGIntension(versions.get(i), versions.get(i + 1)));
            String extension = Constants.formatter.format(calc.getAVGExtension(versions.get(i), versions.get(i + 1)));
            String whole = Constants.formatter.format(calc.getAVGWhole(versions.get(i), versions.get(i + 1)));

            results.add(new AverageDrift(from, to, label, intension, extension, whole));
        }

        return results;
    }

    //******************************************************
    //* Ranking chains
    //******************************************************/
    /**
     * This method calculates the ranking of most stable chains in label
     * approach.
     *
     * @return This returns an arrayList of RankedConcept instances that include
     * name of concept, rank and stability score.
     */
    public ArrayList<RankedConcept> getLabelRanking() {
        return getRanking(Constants.LABEL);
    }

    /**
     * This method calculates the ranking of most stable chains in intensional
     * approach.
     *
     * @return This returns an arrayList of RankedConcept instances that include
     * name of concept, rank and stability score.
     */
    public ArrayList<RankedConcept> getIntensionRanking() {
        return getRanking(Constants.INTENSION);
    }

    /**
     * This method calculates the ranking of most stable chains in extensional
     * approach.
     *
     * @return This returns an arrayList of RankedConcept instances that include
     * name of concept, rank and stability score.
     */
    public ArrayList<RankedConcept> getExtensionRanking() {
        return getRanking(Constants.EXTENSION);
    }

    /**
     * This method calculates the ranking of most stable chains in whole
     * approach.
     *
     * @return This returns an arrayList of RankedConcept instances that include
     * name of concept, rank and stability score.
     */
    public ArrayList<RankedConcept> getWholeRanking() {
        return getRanking(Constants.WHOLE);
    }

    private ArrayList<RankedConcept> getRanking(String type) {

        ArrayList<RankedConcept> results = new ArrayList<>();

        Map<Chain, Double> ranks = new HashMap<>();
        ArrayList<Chain> chains = getChains(type);
        for (Chain chain : chains) {
            ArrayList<Link> links = chain.getLinks();
            double sum = 0;
            for (Link link : links) {
                sum = sum + link.getPair().getStabilityValue();
            }
            double avg = 0;
            if (sum > 0 && links.size() > 0) {
                avg = sum / links.size();
            }
            ranks.put(chain, avg);
        }
        ranks = sortByComparator(ranks);

        int i = 1;
        for (Map.Entry<Chain, Double> entry : ranks.entrySet()) {
            results.add(new RankedConcept(Integer.toString(i), entry.getKey(), Constants.formatter.format(entry.getValue())));
            i++;
        }

        return results;
    }

    /**
     * This method calculates the stability between all concepts of each two
     * consecutive versions in label approach.
     *
     * @return This returns an arrayList of VersionPairs instances that include
     * names of compared versions and Pair instances.
     */
    public ArrayList<VersionPairs> getLabelVersionPairs() {

        ArrayList<VersionPairs> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            ArrayList<ConceptPair> pairs = calc.getLabelPairs(versions.get(i), versions.get(i + 1));
            results.add(new VersionPairs(versions.get(i), versions.get(i + 1), pairs));
        }

        return results;
    }

    /**
     * This method calculates the stability between all concepts of each two
     * consecutive versions in intensional approach.
     *
     * @return This returns an arrayList of VersionPairs instances that include
     * names of compared versions and Pair instances.
     */
    public ArrayList<VersionPairs> getIntensionVersionPairs() {

        ArrayList<VersionPairs> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            ArrayList<ConceptPair> pairs = calc.getIntensionPairs(versions.get(i), versions.get(i + 1));
            results.add(new VersionPairs(versions.get(i), versions.get(i + 1), pairs));
        }

        return results;
    }

    /**
     * This method calculates the stability between all concepts of each two
     * consecutive versions in extensional approach.
     *
     * @return This returns an arrayList of VersionPairs instances that include
     * names of compared versions and Pair instances.
     */
    public ArrayList<VersionPairs> getExtensionVersionPairs() {

        ArrayList<VersionPairs> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            ArrayList<ConceptPair> pairs = calc.getExtensionPairs(versions.get(i), versions.get(i + 1));
            results.add(new VersionPairs(versions.get(i), versions.get(i + 1), pairs));
        }

        return results;
    }

    /**
     * This method calculates the stability between all concepts of each two
     * consecutive versions in whole approach.
     *
     * @return This returns an arrayList of VersionPairs instances that include
     * names of compared versions and Pair instances.
     */
    public ArrayList<VersionPairs> getWholeVersionPairs() {

        ArrayList<VersionPairs> results = new ArrayList<>();

        for (int i = 0; i < versions.size() - 1; i++) {
            ArrayList<ConceptPair> pairs = calc.getWholePairs(versions.get(i), versions.get(i + 1));
            results.add(new VersionPairs(versions.get(i), versions.get(i + 1), pairs));
        }

        return results;
    }

    /**
     * This method finds concept chains in label approach.
     *
     * @return This returns an arrayList of Chain instances.
     */
    public ArrayList<Chain> getLabelChains() {
        return getChains(Constants.LABEL);
    }

    /**
     * This method finds concept chains in intensional approach.
     *
     * @return This returns an arrayList of Chain instances.
     */
    public ArrayList<Chain> getIntensionChains() {
        return getChains(Constants.INTENSION);
    }

    /**
     * This method finds concept chains in extensional approach.
     *
     * @return This returns an arrayList of Chain instances.
     */
    public ArrayList<Chain> getExtensionChains() {
        return getChains(Constants.EXTENSION);
    }

    /**
     * This method finds concept chains in whole approach.
     *
     * @return This returns an arrayList of Chain instances.
     */
    public ArrayList<Chain> getWholeChains() {
        return getChains(Constants.WHOLE);
    }

    private ArrayList<Chain> getChains(String type) {

        ArrayList<Chain> results = new ArrayList<>();
        Map<String, Chain> chains = new HashMap();

        //all maxPairs from first two versions are links
        ArrayList<ConceptPair> pairs = new ArrayList<>();
        switch (type) {
            case Constants.LABEL:
                pairs = calc.getLabelMaxPairs(versions.get(0), versions.get(1));
                break;
            case Constants.INTENSION:
                pairs = calc.getIntensionMaxPairs(versions.get(0), versions.get(1));
                break;
            case Constants.EXTENSION:
                pairs = calc.getExtensionMaxPairs(versions.get(0), versions.get(1));
                break;
            case Constants.WHOLE:
                pairs = calc.getWholeMaxPairs(versions.get(0), versions.get(1));
                break;
        }
        for (ConceptPair pair : pairs) {
            Chain chain = new Chain(pair.getFrom());
            chain.addLink(versions.get(0), versions.get(1), pair);
            chains.put(pair.getFromIRI(), chain);
        }

        for (int i = 1; i < versions.size() - 1; i++) {
            switch (type) {
                case Constants.LABEL:
                    pairs = calc.getLabelMaxPairs(versions.get(i), versions.get(i + 1));
                    break;
                case Constants.INTENSION:
                    pairs = calc.getIntensionMaxPairs(versions.get(i), versions.get(i + 1));
                    break;
                case Constants.EXTENSION:
                    pairs = calc.getExtensionMaxPairs(versions.get(i), versions.get(i + 1));
                    break;
                case Constants.WHOLE:
                    pairs = calc.getWholeMaxPairs(versions.get(i), versions.get(i + 1));
                    break;
            }
            for (ConceptPair pair : pairs) {
                boolean isNew = true;
                for (Map.Entry<String, Chain> entry : chains.entrySet()) {
                    Chain chain = entry.getValue();
                    if (pair.getFrom().equals(chain.getLastLink())) {
                        chain.addLink(versions.get(i), versions.get(i + 1), pair);
                        chains.put(entry.getKey(), chain);
                        isNew = false;
                        break;
                    }
                }
                if (isNew) {
                    Chain chain = new Chain(pair.getFrom());
                    chain.addLink(versions.get(i), versions.get(i + 1), pair);
                    chains.put(pair.getFromIRI(), chain);
                }
            }
        }

        for (Map.Entry<String, Chain> entry : chains.entrySet()) {
            results.add(entry.getValue());
        }

        return results;
    }

    // UTIL
    private static Map<Chain, Double> sortByComparator(Map<Chain, Double> unsortMap) {

        List<Map.Entry<Chain, Double>> list = new LinkedList<>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Chain, Double>>() {
            @Override
            public int compare(Map.Entry<Chain, Double> o1, Map.Entry<Chain, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<Chain, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Chain, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

}
