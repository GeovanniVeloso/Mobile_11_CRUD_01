package br.edu.fateczl.mobile_11_crud_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import controller.TeamController;
import model.Team;
import persistance.TeamDao;

public class TeamFragment extends Fragment {

    private TeamController tc;
    private View view;
    public TeamFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team, container, false);
        tc = new TeamController(new TeamDao(view.getContext()));


        return view;

    }

    public void insertAction(){
        Team team = create();

        try {
            tc.insert(team);
            Toast.makeText(view.getContext(), "Time criado com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void updateAction(){
        Team team = create();

        try {
            tc.update(team);
            Toast.makeText(view.getContext(), "Time criado com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void deleteAction(){
        Team team = create();

        try {
            tc.delete(team);
            Toast.makeText(view.getContext(), "Time criado com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void search(){
        Team team = create();

        try {
            team = tc.search(team);
            if(team.getName() != null){
                fill(team);
            }else{
                Toast.makeText(view.getContext(), "Time não encontrado", Toast.LENGTH_LONG).show();
                clear();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void list(){

        try {
            List<Team> teams = tc.list();
            StringBuffer stringBuffer = new StringBuffer();
            for(Team t : teams){
                stringBuffer.append(t.toString()+"\n");
            }
            //setText(stringBuffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void clear(){

    }
    public Team create(){
        Team team = new Team();

        return team;
    }

    public void fill(Team team){

    }
}