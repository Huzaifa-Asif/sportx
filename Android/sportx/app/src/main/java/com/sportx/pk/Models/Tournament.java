package com.sportx.pk.Models;

public class Tournament {
    private String tournamentId;
    private String state;
    private String name;
    private String teams;
    private String winningPrize;
    private String entryFee;
    private String tournamentType;
    private String maxDays;
    private String serviceProviderEmail;
    private String adderEmail;
    private String startDate;
    private String startTime;
    private String date;

    public Tournament(String tournamentId, String state, String name, String teams, String winningPrize, String entryFee, String tournamentType, String maxDays, String serviceProviderEmail, String adderEmail, String startDate,String startTime, String date) {
        this.tournamentId = tournamentId;
        this.state = state;
        this.name = name;
        this.teams = teams;
        this.winningPrize = winningPrize;
        this.entryFee = entryFee;
        this.tournamentType = tournamentType;
        this.maxDays = maxDays;
        this.serviceProviderEmail = serviceProviderEmail;
        this.adderEmail = adderEmail;
        this.startDate = startDate;
        this.startTime = startTime;
        this.date = date;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public String getTournamentState() {
        return state;
    }

    public String getTournamentName() {
        return name;
    }

    public String getTournamentTeam() {
        return teams;
    }

    public String getTournamentWinningPrize() {
        return winningPrize;
    }

    public String getTournamentEntryFee() {
        return entryFee;
    }

    public String getTournamentType() {
        return tournamentType;
    }

    public String getTournamentMaxDays() {
        return maxDays;
    }

    public String getTournamentServiceProviderEmail() {
        return serviceProviderEmail;
    }

    public String getTournamentAdderEmail() {
        return adderEmail;
    }

    public String getTournamentStartDate() {
        return startDate;
    }

    public String getTournamentStartTime() {
        return startTime;
    }

    public String getTournamentRecordDate() {
        return date;
    }
}
