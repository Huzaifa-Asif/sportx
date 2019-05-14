var bookingDetails =require('../models/bookingDetails.js');

// Get bookingDetails
module.exports.getBookingDetails = (callback, limit) => {
	bookingDetails.find(callback).limit(limit);
}

// Get bookingDetails By ID
module.exports.getBookingDetailsById = (id ,callback) =>  {
	bookingDetails.findById(id, callback);
}

// Add bookingDetails
module.exports.addbookingDetails = (bookingDetailsform, callback) => {
	bookingDetails.create(bookingDetailsform, callback);
}

// Update bookingDetails
module.exports.updateBookingDetails = (id, bookingDetailsform, options, callback) => {
    var query = {_id: id};
    var update = {

        bookingType: bookingDetailsform.bookingType,
        price: bookingDetailsform.price,
        serviceProviderId: bookingDetailsform.serviceProviderId,
        
    }

    bookingDetails.findOneAndUpdate(query, update, options, callback);
}

// Delete bookingDetails   
module.exports.removeBookingDetails = (id, callback) => {
    var query = {_id: id};
    bookingDetails.remove(query, callback);
}