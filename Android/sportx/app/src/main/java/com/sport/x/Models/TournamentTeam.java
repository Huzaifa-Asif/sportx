package com.sport.x.Models;

import org.json.JSONArray;

public class TournamentTeam {

    private String state;
    private String name;
//    private String [] players = new String[15];
    private String teamId;
    private String tournamentId;
    private String adderEmail;
    private String teamContact;

    JSONArray players;

    public TournamentTeam(String state, String name, JSONArray players,String teamId,String tournamentId, String adderEmail, String teamContact)
    {
        this.state = state;
        this.name = name;
        this.players = players;
        this.tournamentId = tournamentId;
        this.teamId = teamId;
        this.adderEmail = adderEmail;
        this.teamContact = teamContact;

    }

    public String getTournamentTeamState() {
        return state;
    }

    public String getTournamentTeamName() {
        return name;
    }

    public JSONArray getTournamentTeamPlayers() {
        return players;
    }

    public String getTournamentTeamId() {
        return teamId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public String getTournamentTeamAdderEmail() {
        return adderEmail;
    }

    public String getTournamentTeamContact() {
        return teamContact;
    }


}
