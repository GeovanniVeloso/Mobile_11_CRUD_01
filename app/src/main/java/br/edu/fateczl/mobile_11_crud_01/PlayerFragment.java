package br.edu.fateczl.mobile_11_crud_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import controller.TeamController;
import model.Player;
import model.Team;
import persistance.PlayerDao;
import persistance.TeamDao;

public class PlayerFragment extends Fragment {

    private View view;

    private EditText etNumberP;
    private EditText etNameP;
    private EditText etDateP;
    private EditText etHeightP;
    private EditText etWeightP;
    private TextView tvResP;
    private Spinner spTeamP;
    private Button btRegisterP;
    private Button btUpdateP;
    private Button btSearchP;
    private Button btDeleteP;
    private Button btListP;

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

        etNumberP = view.findViewById(R.id.etNumberP);
        etNameP = view.findViewById(R.id.etNameP);
        etDateP = view.findViewById(R.id.etDateP);
        etHeightP = view.findViewById(R.id.etHeightP);
        etWeightP = view.findViewById(R.id.etWeightP);
        spTeamP = view.findViewById(R.id.spTeamP);
        btRegisterP = view.findViewById(R.id.btRegisterP);
        btUpdateP = view.findViewById(R.id.btUpdateP);
        btSearchP = view.findViewById(R.id.btSearchP);
        btDeleteP = view.findViewById(R.id.btDeleteP);
        btListP = view.findViewById(R.id.btListP);
        tvResP = view.findViewById(R.id.tvResP);

        btRegisterP.setOnClickListener(op -> insertAction());
        btUpdateP.setOnClickListener(op -> updateAction());
        btSearchP.setOnClickListener(op -> searchAction());
        btDeleteP.setOnClickListener(op -> deleteAction());
        btListP.setOnClickListener(op -> listAction());

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
            spTeamP.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertAction(){

        int spPos = spTeamP.getSelectedItemPosition();
        if(spPos > 0){
            Player player = create();
            try {
                pc.insert(player);
                Toast.makeText(view.getContext(), "Jogador inserido com sucesso.", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }

    }
    public void updateAction(){

        int spPos = spTeamP.getSelectedItemPosition();
        if(spPos > 0){
            Player player = create();
            try {
                pc.update(player);
                Toast.makeText(view.getContext(), "Jogador atualizado com sucesso.", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }

    }
    public void deleteAction(){

        Player player = create();

        try {
            pc.delete(player);
            Toast.makeText(view.getContext(), "Jogador deletado com sucesso.", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        clear();

    }
    public void searchAction(){

        Player player = create();
        try {
            teams = tc.list();
            player = pc.search(player);
            if(player.getName() != null){
                fill(player);
            }else{
                Toast.makeText(view.getContext(), "Jogador não encontrado.", Toast.LENGTH_LONG).show();
                clear();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void listAction(){

        List<Player>players = new ArrayList<>();
        try {
            players = pc.list();
            StringBuffer buffer = new StringBuffer();
            for (Player p: players) {
                buffer.append(p.toString() + "\n");
            }
            tvResP.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public void clear(){

        etNumberP.setText("");
        etNameP.setText("");
        etDateP.setText("");
        etHeightP.setText("");
        etWeightP.setText("");
        spTeamP.setSelection(0);

    }
    public Player create(){

        Player player = new Player();

        player.setNumber(Integer.parseInt(etNumberP.getText().toString()));
        player.setName(etNameP.getText().toString());
        player.setBirthDate(LocalDate.parse(etDateP.getText().toString()));
        player.setHeight(Float.parseFloat(etHeightP.getText().toString()));
        player.setWeight(Float.parseFloat(etWeightP.getText().toString()));
        player.setTeam((Team) spTeamP.getSelectedItem());

        return player;
    }
    public void fill(Player player){

        etNumberP.setText(player.getNumber());
        etNameP.setText(player.getName());
        etDateP.setText(player.getBirthDate().toString());
        etHeightP.setText(String.valueOf(player.getHeight()));
        etWeightP.setText(String.valueOf(player.getWeight()));

        int cont = 1;
        for (Team t: teams) {
            if(t.getId() == player.getTeam().getId()){
                spTeamP.setSelection(cont);
            }else{
                cont++;
            }
        }
        if (cont > teams.size()){
            spTeamP.setSelection(0);
        }

    }

}