const mongoose = require('mongoose');

const schema = mongoose.Schema;

// Booking Schema

const bookingSchema = new schema({
    bookingType:{
        type:String
    },
    date:{
        type:Date
    },
    time:{
        type:String
    },
    status:{
        type:String,
        default:'pending'
    },
    serviceProviderId:{
        type:String
    },
    customerId:{
        type:String
    }
})


const booking= module.exports = mongoose.model('Booking',bookingSchema);