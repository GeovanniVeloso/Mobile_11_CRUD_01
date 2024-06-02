package br.edu.fateczl.mobile_11_crud_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import controller.PlayerController;
import controller.TeamController;
import model.Player;
import model.Team;
import persistance.PlayerDao;
import persistance.TeamDao;

public class PlayerFragment extends Fragment {

    private View view;
    private TeamController tc;
    private PlayerController pc;
    private List<Team> teams;

    public PlayerFragment() {
       super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_player, container, false);

        tc = new TeamController(new TeamDao(view.getContext()));
        pc = new PlayerController(new PlayerDao(view.getContext()));
        spinnerFill();

        return view;
    }

    private void spinnerFill() {
        Team team = new Team();
        team.setId(0);
        team.setName("Selecione um Time");
        team.setCity("");

        try {
            teams = tc.list();
            teams.add(0, team);

            ArrayAdapter arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, teams);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //id do spinner.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertAction(){

    }
    public void updateAction(){

    }
    public void deleteAction(){

    }
    public void search(){

    }
    public void list(){

    }
    public void clear(){

    }
    public Player create(){

    }
    public void fill(Player player){

    }

}