var base64Img = require('base64-img');

module.exports.uploadPicture= (name,base64) =>
{
    base64Img.img
    ('data:image/png;base64,'+base64,             // Base64 String
    'uploads',                                   //Destination Path
    name,                                     //Image Name
    function(err) 
    {
        if(err)
        {
            console.log(err);
        }
    });
    return 'uploads/'+name+'.png';
} 