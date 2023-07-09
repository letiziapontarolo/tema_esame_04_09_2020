package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private Graph<Movie, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Movie> movieIdMap;
	
	public Model () {
		
		dao = new ImdbDAO();
		
		
	}
	
	public void creaGrafo(double rank) {
		
		movieIdMap = new HashMap<Integer, Movie>();
		grafo = new SimpleWeightedGraph<Movie, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.dao.creaVertici(movieIdMap);
		Graphs.addAllVertices(this.grafo, movieIdMap.values());

		for (Arco a : this.dao.listaArchi(rank)) {
			Graphs.addEdgeWithVertices(this.grafo, movieIdMap.get(a.getM1()), 
							movieIdMap.get(a.getM2()), a.getPeso());
		}
	}
	
	public String gradoMassimo() {
		
		String result = "";
		Movie filmGradoMassimo = null;
		double gradoMassimo = 0;
		
		for (Movie m : this.grafo.vertexSet()) {
			double sommaPesi = 0;
			Set<DefaultWeightedEdge> archiIncidenti = grafo.edgesOf(m);
			for (DefaultWeightedEdge e : archiIncidenti) {
				sommaPesi = sommaPesi + this.grafo.getEdgeWeight(e);
				
			}
			
			if (sommaPesi > gradoMassimo) {
				gradoMassimo = sommaPesi;
				filmGradoMassimo = new Movie(m.getId(), m.getName(), m.getYear(), m.getRank());
			}
		}
		
		result = "\nFILM GRADO MASSIMO:\n" + filmGradoMassimo.getId() + 
				" - " + filmGradoMassimo.getName() + " (" + (int)gradoMassimo + ")\n";
		
		return result;
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
		}
	
		 public int numeroArchi() {
		return this.grafo.edgeSet().size();
		}
	

	

	
}
