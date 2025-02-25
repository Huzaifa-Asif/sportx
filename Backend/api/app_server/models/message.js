const mongoose = require('mongoose');

const schema = mongoose.Schema;

// Message  Schema

const messageSchema = new schema({
    // _id of Conversation 
    conversationId:{
        type:String
    },
    senderEmail:{
        type:String
    },
    message:{
        type:String
    },
    type:{
        type:String,
        default:"text"
    },
    image_path:{
        type:String
    },
    date:{
        type:String
    },
    time:{
        type:String
    },
})


// const Message= module.exports = mongoose.model('Message',messageSchema);
// Message.deleteMany({conversationId:"5d41bdcc8c729b3568319168"},function(err,data)
// {
//     if(err)
//     console.log(err);
//     else
//     console.log(data);
// })