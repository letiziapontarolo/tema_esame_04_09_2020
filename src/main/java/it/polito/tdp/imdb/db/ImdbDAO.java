package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
public void creaVertici(Map<Integer, Movie> movieIdMap) {
		
		String sql = "SELECT * "
				+ "FROM movies "
				+ "WHERE movies.rank IS NOT NULL "
				+ "GROUP BY movies.id";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie m = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				movieIdMap.put(res.getInt("id"), m);
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

    public List<Arco> listaArchi(double rank) {
	
	String sql = "SELECT m1.id AS movie1, m2.id AS movie2, COUNT(DISTINCT a.id) AS peso "
			+ "FROM movies m1, roles r1, actors a, movies m2, roles r2 "
			+ "WHERE m1.id > m2.id AND m1.rank >= ? AND m2.rank >= ? "
			+ "AND a.id = r1.actor_id AND a.id = r2.actor_id "
			+ "AND m1.id = r1.movie_id AND m2.id = r2.movie_id "
			+ "GROUP BY m1.id, m2.id";
	
	List<Arco> archi = new ArrayList<Arco>();
	
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setDouble(1, rank);
		st.setDouble(2, rank);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Arco a = new Arco(res.getInt("movie1"), res.getInt("movie2"), res.getInt("peso"));
			archi.add(a);
		}
		conn.close();
		return archi;

		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}

}


	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
