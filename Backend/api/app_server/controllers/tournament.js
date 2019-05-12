require('../models/tournament.js');
var mongoose = require('mongoose');
var tournament = mongoose.model('Tournament');


// Get tournament
module.exports.getTournament = (callback, limit) => {
	tournament.find(callback).limit(limit);
}

// Get tournament By ID
module.exports.getTournamentById = (id ,callback) =>  {
	tournament.findById(id, callback);
}

// Add tournament
module.exports.addTournament = (tournamentform, callback) => {
	tournament.create(tournamentform, callback);
}

// Update tournament
module.exports.updateTournament = (id, tournamentform, options, callback) => {
    var query = {_id: id};
    var update = {
        name: tournamentform.name,
        teams:tournamentform.teams,
        winningPrize: tournamentform.winningPrize,
        entryFee: tournamentform.entryFee,
        tournamentType:tournamentform.tournamentType,
        maxDays: tournamentform.maxDays,
        serviceProvierId: tournamentform.serviceProvierId
    }

    tournament.findOneAndUpdate(query, update, options, callback);
}

// Delete tournament
module.exports.removeTournament = (id, callback) => {
    var query = {_id: id};
    tournament.remove(query, callback);
}
