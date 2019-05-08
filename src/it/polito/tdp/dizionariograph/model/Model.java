package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;


public class Model {
	private Graph<String, DefaultEdge> grafo ;
	private List<String> parole ;
	
	
	public void createGraph(int numeroLettere) {
		int differenze=0;
		this.grafo = new SimpleGraph<>(DefaultEdge.class) ;
		
		WordDAO dao = new WordDAO() ;
		this.parole = dao.getAllWordsFixedLength(numeroLettere);
		
		
		
		
		Graphs.addAllVertices(this.grafo, this.parole) ;
	
		
		for(String s: grafo.vertexSet()) {
			for(String s2: grafo.vertexSet()) {
				if(!s.equals(s2)) 
				{ differenze=0;
					for(int i=0;i<s.length();i++) {
						if(s.charAt(i) !=s2.charAt(i))
							differenze++;
					}
					if(differenze==1)
						grafo.addEdge(s, s2);
				}
			}
		}
		
		
		
		
		//System.err.println(grafo);
	}

	public Graph<String, DefaultEdge> getGrafo() {
		return grafo;
	}

	

	public List<String> displayNeighbours(String parolaInserita) {
		int length=parolaInserita.length();
		WordDAO dao = new WordDAO() ;
		int differenze;
		List<String> sameL =new ArrayList<String>(parole);
		List<String> result= new ArrayList<String>();
		
		for(String s2: sameL) {
			/*
			 if(!parolaInserita.equals(s2)) 
			 
			{ differenze=0;
				for(int i=0;i<length;i++) {
					if(parolaInserita.charAt(i) !=s2.charAt(i))
						differenze++;
				}
				if(differenze==1)
				*/
					
				if(grafo.containsEdge(s2, parolaInserita)) {
					result.add(s2);
					}
			}
		
		//return Graphs.neighborListOf(grafo, parolaInserita);
		
		return result;
	}

	public int findMaxDegree() {
		int maxG=0;
		for(String s: grafo.vertexSet()) {
			if(grafo.degreeOf(s)> maxG)
				maxG= grafo.degreeOf(s);
		}
		//System.err.println("findMaxDegree -- TODO");
		return maxG;
	}
}
