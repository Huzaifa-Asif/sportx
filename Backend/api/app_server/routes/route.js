var express = require('express');
var router = express.Router();


var admin = require('../controllers/admin.js');
var booking = require('../controllers/booking.js');
var bookingDetails = require('../controllers/bookingDetails.js');
var chatbox = require('../controllers/chatbox.js');
var customer = require('../controllers/customer.js');
var expense = require('../controllers/expense.js');
var expenseCategory = require('../controllers/expenseCategory.js');
var ratingAndFeedback = require('../controllers/ratingAndFeedback.js');
var revenue = require('../controllers/revenue.js');
var revenueCategory = require('../controllers/revenueCategory.js');
var serviceProvider = require('../controllers/serviceProvider.js');
var team = require('../controllers/team.js');
var tournament = require('../controllers/tournament.js');


// Get Service Provider by category
router.get('/', function (req, res) {

  return res.json("Welcome Sport-X API's");

});

//Add Admin
router.post('/signup_Admin', function (req, res) {
    var adminform=req.body;
    admin.addAdmin(adminform,function (err, admin) {
        if (err) {
            console.log(err);
            return res.status(500).json({Message:"Error in Connecting to DB"});
                }
        return res.json(admin);
        });

});


// Signup for customer
router.post('/signup_customer', function (req, res) {
    var customerform=req.body;
    customer.getCustomerByEmail(customerform.email,function(err,result)
    {
        if(err)
        return res.status(500).json({Message:"Error in Connecting to DB"});
        else
        {
            if(result)
            return res.json({Message:"Email Already Exists"});
            else
            {
                serviceProvider.getServiceProviderByEmail(customerform.email,function(err,result)
                {
                    if(err)
                    return res.status(500).json({Message:"Error in Connecting to DB"});
                    else if(result)
                    return res.json({Message:"Email Already Exists"});
                    else
                    {
                        customer.addCustomer(customerform,function (err, customer)
                        {
                            if (err)
                            {
                            return res.status(500).json({Message:"Error in Connecting to DB"});
                            }
                            return res.json(customer);

                        });
                    }
                });
            }
         }

     });
});


// Signup for serviceProvider
router.post('/signup_serviceProvider', function (req, res) {
    var serviceProviderform=req.body;
    serviceProvider.getServiceProviderByEmail(serviceProviderform.email,function(err,result)
    {
        if(err)
        return res.status(500).json({Message:"Error in Connecting to DB"});
        else
        {
            if(result)
            return res.json({Message:"Email Already Exists"});
            else
            {
                customer.getCustomerByEmail(serviceProviderform.email,function(err,result)
                {
                    if(err)
                    return res.status(500).json({Message:"Error in Connecting to DB"});
                    else
                    {
                        if(result)
                        return res.json({Message:"Username Already Exists"});
                        else
                        {
                            serviceProvider.addServiceProvider(serviceProviderform,function (err, serviceProvider) {
                                if (err) {
                                    return res.status(500).json({Message:"Error in Connecting to DB"});
                                }
                                return res.json(serviceProvider);

                            });
                        }

                    }
                });

            }
         }

     });
});

// Login for both serviceProvider and Customer
router.post('/login', function (req, res) {
    let email=req.body.email;
    let password=req.body.password;
    customer.login(email,password,res);
    serviceProvider.login(email,password,res);

});

//Login for Admin
router.post('/loginAdmin', function (req, res) {
    let email=req.body.email;
    let password=req.body.password;
    admin.login(email,password,res);


});

// Get Service Provider by category
router.get('/search/serviceProviderByCategory/:category', function (req, res) {
    serviceProvider.getServiceProviderByCategory(req.params.category,function (err, serviceProviders) {
        if (err) {
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        else if(serviceProviders)
        return res.json(serviceProviders);
        else
        return res.status(500).json({Message:"No service Providers found with the category "+req.params.category});
    });

});


// Get Service Provider by name
router.get('/search/serviceProviderByName/:name', function (req, res) {
    serviceProvider.getServiceProviderByName(req.params.name,function (err, serviceProviders) {
        if (err) {
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        else if(serviceProviders)
        return res.json(serviceProviders);
        else
        return res.status(500).json({Message:"No service Providers found with Names like "+req.params.name});

    });

});


// Get Service Provider by address
router.get('/search/serviceProviderByAddress/:keyword', function (req, res) {
    serviceProvider.getServiceProviderByAddress(req.params.keyword,function (err, serviceProviders) {
        if (err) {
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        else if(serviceProviders)
        return res.json(serviceProviders);
        else
        return res.status(500).json({Message:"No service Providers found with address containing "+req.params.keyword});

    });

});


// Get Service Provider by location
router.get('/search/serviceProviderByLocation/:lat/:long/:maxDistance', function (req, res) {
    serviceProvider.getServiceProviderByLocation(req.params.lat,req.params.long,req.params.maxDistance,function (err, serviceProviders) {
        if (err) {
            console.log(err);
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        else if(serviceProviders)
        return res.json(serviceProviders);
        else
        return res.status(500).json({Message:"No service Providers found within specified area"});

    });

});


// Update Customer Profile
router.patch('/update_customer/:email', function (req, res) {
    var email = req.params.email;
    var customerform = req.body;
    customer.updateCustomer(email, customerform, {new:true}, function (err, customer) {
        if (err) {
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        return res.json(customer);

    });

});


// Update serviceProvider Profile
router.patch('/update_serviceProvider/:email', function (req, res) {
    var email = req.params.email;
    var serviceProviderform = req.body;
    serviceProvider.updateServiceProvider(email, serviceProviderform, {new:true}, function (err, serviceProvider) {
        if (err) {
            return res.status(500).json({Message:"Error in Connecting to DB"});
        }
        return res.json(serviceProvider);

    });

});



// /* GET home page. */
// router.get('/', function (req, res, next) {
//     res.send(`
//     <html>
//     <div style="text-align:center;">
//     <h2>
//     <br>
//     <p>  Please use /api/stocks/    or   /api/demands/ </p>

//     <a href="http://localhost:3000/api/stocks/" target='blank'> Stock API </a>
//     &ensp;  &ensp;
//      <a href="http://localhost:3000/api/demands/" target='blank'> Demand API </a>
//    </h2>
//    </div>
//     </html>


//     `);
// });


// // View Stocks
// router.get('/api/stocks/', function (req, res) {
//     Stock.getStocks(function (err, Stocks) {
//         if (err) {
//             throw err;
//         }
//         res.json(Stocks);

//     });

// });

// // Find Stock By Id
// router.get('/api/stocks/:_id', function (req, res) {
//     Stock.getStockById(req.params._id, function (err, Stock) {
//         if (err) {
//             throw err;
//         }
//         res.json(Stock);

//     });

// });

// // Add Stocks
// router.post('/api/stocks/', function (req, res) {
//     var stockform = req.body;
//     Stock.addStock(stockform, function (err, stockform) {
//         if (err) {
//             throw err;
//         }
//         res.json(stockform);

//     });

// });

// // Update Stocks
// router.put('/api/stocks/:_id', function (req, res) {
//     var id = req.params._id;
//     var stockform = req.body;
//     Stock.updateStock(id, stockform, {}, function (err, stockform) {
//         if (err) {
//             throw err;
//         }
//         res.json(stockform);

//     });

// });


// // Delete Stocks
// router.delete('/api/stocks/:_id', function (req, res) {
//     var id = req.params._id;
//     Stock.removeStock(id, function (err, stock) {
//         if (err) {
//             throw err;
//         }
//         res.json("Stock Deleted Successfully");

//     });

// });


// // View Demands
// router.get('/api/demands/', function (req, res) {
//     Demand.getDemands(function (err, Demands) {
//         if (err) {
//             throw err;
//         }
//         res.json(Demands);

//     });

// });

// // Find Demand By Id
// router.get('/api/demands/:_id', function (req, res) {
//     Demand.getDemandById(req.params._id, function (err, Demand) {
//         if (err) {
//             throw err;
//         }
//         res.json(Demand);

//     });

// });

// // Add Demands
// router.post('/api/demands/', function (req, res) {
//     var demandform = req.body;
//     Demand.addDemand(demandform, function (err, demandform) {
//         if (err) {
//             throw err;
//         }
//         res.json(demandform);

//     });

// });

// // Update Demand
// router.put('/api/demands/:_id', function (req, res) {
//     var id = req.params._id;
//     var demandform = req.body;
//     Demand.updateDemand(id, demandform, {}, function (err, demandform) {
//         if (err) {
//             throw err;
//         }
//         res.json(demandform);

//     });

// });


// // Delete Demand
// router.delete('/api/demands/:_id', function (req, res) {
//     var id = req.params._id;
//     Demand.removeDemand(id, function (err, demand) {
//         if (err) {
//             throw err;
//         }
//         res.json(demand);

//     });

// });


module.exports = router;
