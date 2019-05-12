const mongoose = require('mongoose');

const schema = mongoose.Schema;

// Booking Details Schema

const bookingDetailsSchema = new schema({
    bookingType:{
        type:String
    },
    price:{
        type:Number
    },
    serviceProviderId:{
        type:String
    }
})


const bookingDetails= module.exports = mongoose.model('BookingDetails',bookingDetailsSchema);