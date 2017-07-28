var exec = require('cordova/exec');

exports.open = function(success, error) {
    exec(success, error, "IdCard", "open", []);
};

exports.read = function(success, error) {
    exec(success, error, "IdCard", "read", []);
};

exports.close = function(success, error) {
    exec(success, error, "IdCard", "close", []);
};

exports.ssFopen = function(success, error) {
    exec(success, error, "IdCard", "ssFopen", []);
};

exports.ssFget = function(success, error) {
    exec(success, error, "IdCard", "ssFget", []);
};

exports.ssFgetHex = function(success, error) {
    exec(success, error, "IdCard", "ssFgetHex", []);
};

exports.ssUp = function(success, error) {
    exec(success, error, "IdCard", "ssUp", []);
};

exports.Comparison = function(fingerInfo, success, error) {
    exec(success, error, "IdCard", "Comparison", [fingerInfo]);
};

exports.idComparison = function(success, error) {
    exec(success, error, "IdCard", "idComparison", []);
};