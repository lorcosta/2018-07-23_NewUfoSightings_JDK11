package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates(Map<String,State> idMapState) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				idMapState.put(state.getId(), state);
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Confine> getStatiConfinanti(String shape, Integer year, Map<String, State> idMapState){
		String sql="SELECT n.state1, n.state2, COUNT(*) AS peso " + 
				"FROM neighbor n, sighting s1, sighting s2 " + 
				"WHERE s1.shape=s2.shape AND s1.shape=? AND YEAR(s1.datetime)=YEAR(s2.datetime) "+
				"AND YEAR(s1.datetime)=? AND s1.state=n.state2 AND s2.state=n.state1 " + 
				"GROUP BY n.state1, n.state2";
		List<Confine> confini= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, shape);
			st.setInt(2, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				confini.add(new Confine(idMapState.get(rs.getString("state1")),
						idMapState.get(rs.getString("state2")),rs.getDouble("peso")));
			}

			conn.close();
			return confini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

		
	}
	public List<String> getForme(Integer anno) {
		String sql="SELECT DISTINCT shape " + 
				"FROM sighting " + 
				"WHERE YEAR(datetime)=?";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String shape=rs.getString("shape");
				if(!shape.isBlank()) {
					result.add(shape);
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}

