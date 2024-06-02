package br.edu.fateczl.mobile_11_crud_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import controller.TeamController;
import model.Team;
import persistance.TeamDao;

public class TeamFragment extends Fragment {

    private TeamController tc;
    private View view;

    private EditText etIdT;
    private EditText etNameT;
    private EditText etCityT;
    private TextView tvResT;
    private Button btRegisterT;
    private Button btUpdateT;
    private Button btSearchT;
    private Button btDeleteT;
    private Button btListT;
    public TeamFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_team, container, false);
        tc = new TeamController(new TeamDao(view.getContext()));
        etIdT = view.findViewById(R.id.etIdT);
        etNameT = view.findViewById(R.id.etNameT);
        etCityT = view.findViewById(R.id.etCityT);
        btRegisterT = view.findViewById(R.id.btRegisterT);
        btUpdateT = view.findViewById(R.id.btUpdateT);
        btSearchT = view.findViewById(R.id.btSearchT);
        btDeleteT = view.findViewById(R.id.btDeleteT);
        btListT = view.findViewById(R.id.btListT);
        tvResT = view.findViewById(R.id.tvResT);

        btRegisterT.setOnClickListener(op -> insertAction());
        btUpdateT.setOnClickListener(op -> updateAction());
        btSearchT.setOnClickListener(op -> searchAction());
        btDeleteT.setOnClickListener(op -> deleteAction());
        btListT.setOnClickListener(op -> listAction());

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
    public void searchAction(){
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
    public void listAction(){

        try {
            List<Team> teams = tc.list();
            StringBuffer stringBuffer = new StringBuffer();
            for(Team t : teams){
                stringBuffer.append(t.toString()+"\n");
            }
            tvResT.setText(stringBuffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        clear();
    }
    public void clear(){

        etIdT.setText("");
        etNameT.setText("");
        etCityT.setText("");

    }
    public Team create(){

        Team team = new Team();

        team.setId(Integer.parseInt(etIdT.getText().toString()));
        team.setName(etNameT.toString());
        team.setCity(etCityT.getText().toString());

        return team;
    }

    public void fill(Team team){

        etIdT.setText(team.getId());
        etNameT.setText(team.getName());
        etCityT.setText(team.getCity());

    }
}