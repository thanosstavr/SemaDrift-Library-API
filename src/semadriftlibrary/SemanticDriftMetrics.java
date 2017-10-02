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

import java.io.FileNotFoundException;
import semadriftlibrary.Constructors.OntProperty;
import semadriftlibrary.Constructors.OntClass;
import semadriftlibrary.Constructors.AverageDrift;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import semadriftlibrary.Constructors.RankedConcept;
import semadriftlibrary.Constructors.Version;
import semadriftlibrary.Constructors.VersionPairs;
import semadriftlibrary.Exceptions.OntologyCreationException;

/**
 *
 * @author andreadisst
 */
public class SemanticDriftMetrics {

    public static void main(String[] args) throws OntologyCreationException {

        //Example
        // Ontologies can be found at SemanticDriftMetrics/versions/20xx/tate_20xx.owl
        String path = System.getProperty("user.dir") + "\\versions\\";

        SemanticDrift sd = new SemanticDrift();
        try {
            sd.addVersionFile(path + "2003\\tate_2003.owl");
            sd.addVersionFile(path + "2004\\tate_2004.owl");
            sd.addVersionFile(path + "2006\\tate_2006.owl");
            sd.addVersionFile(path + "2007\\tate_2007.owl");
            sd.addVersionFile(path + "2008\\tate_2008.owl");
            sd.addVersionFile(path + "2010\\tate_2010.owl");
            sd.addVersionFile(path + "2011\\tate_2011.owl");
            sd.addVersionFile(path + "2012\\tate_2012.owl");
            sd.addVersionFile(path + "2013\\tate_2013.owl");
//            sd.addVersionFile(path + "OWL-S 1.0\\Profile 1.owl");
//          sd.addVersionFile(path + "OWL-S 1.2\\Profile.owl");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SemanticDriftMetrics.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*ArrayList<Version> versions = sd.getVersions();
            for(Version version : versions){
            System.out.println(version.getName());
            ArrayList<OntClass> tree = version.getTree();
            for(OntClass leaf : tree){
            print(leaf);
            }
        }*/
        
        /*ArrayList<AverageDrift> avgds = sd.getAverageDrift();
        for (AverageDrift avgd : avgds) {
            System.out.println(avgd.getFrom());
            System.out.println(avgd.getTo());
            System.out.println(avgd.getLabel());
            System.out.println(avgd.getIntension());
            System.out.println(avgd.getExtension());
            System.out.println(avgd.getWhole());
            System.out.println("-------");
        }*/

        /*ArrayList<AverageDrift> avgdhs = sd.getAverageDriftChain();
         for(AverageDrift avgd : avgdhs){
         System.out.println(avgd.getFrom());
         System.out.println(avgd.getTo());
         System.out.println(avgd.getLabel());
         System.out.println(avgd.getIntension());
         System.out.println(avgd.getExtension());
         System.out.println(avgd.getWhole());
         System.out.println("-------");
         }*/
        
        /*ArrayList<Chain> chains = sd.getWholeChains();
         for(Chain chain : chains){
         System.out.println("Chain for " + chain.getInitialConcept());
         ArrayList<Link> links = chain.getLinks();
         for(Link link : links){
         System.out.println(link.getFrom()+"#"+link.getPair().getFrom());
         System.out.println(link.getTo()+"#"+link.getPair().getTo());
         System.out.println(link.getPair().getStability());
         }
         }*/
        
        ArrayList<RankedConcept> rankedConcepts = sd.getExtensionRanking();
         for(RankedConcept rankedConcept : rankedConcepts){
         System.out.println(rankedConcept.getRank());
         System.out.println(rankedConcept.getChain().getInitialConcept());
         System.out.println(rankedConcept.getStrength());
         System.out.println("-------");
         }
        
        /*ArrayList<VersionPairs> pairs = sd.getIntensionVersionPairs();
        for(VersionPairs pair : pairs){
            System.out.println(pair.getFrom());
            System.out.println(pair.getTo());
            System.out.print(String.format("%20s",""));
            for(String to : pair.getXAxis()){
            System.out.print(String.format("%20s",to));
            }
            System.out.println(String.format("%20s",""));
            for(String from : pair.getYAxis()){
            System.out.print(String.format("%20s",from));
            for(String to : pair.getXAxis()){
            System.out.print(String.format("%20s",pair.getStabilityForPair(from, to)));
            }
            System.out.println(String.format("%20s",""));
            }
        }*/
    }

    private static void print(OntClass leaf) {
        System.out.println(leaf.getName());
        for (OntProperty property : leaf.getProperties()) {
            System.out.println(property.getName() + "<" + property.getDomain() + "," + property.getRange() + ">");
        }
        for (OntClass subleaf : leaf.getSubclasses()) {
            print(subleaf);
        }
    }

}
