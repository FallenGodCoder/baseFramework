/**
 * Created by tangcheng on 2016/1/20.
 */
;(function (root, factory) {

    /* CommonJS */
    if (typeof module == 'object' && module.exports) module.exports = factory()

    /* AMD module */
    else if (typeof define == 'function' && define.amd) define(factory)

    /* Browser global */
    else root.ObjectUitl = root.$$ = factory();
}(this,function(){
    var plugin = {
        $$noop:undefined,
        cc:function(callback){
            return function(){
                callback && typeof callback === "function" && callback.apply(this, arguments);
                return this;
            }
        },
        co:function(args){
            return new Object(args);
        },
        csp:function(obj, prop, propValue, isOverWrite){
            var undefined;
            if(isOverWrite === undefined){
                isOverWrite = true;
            }
            if(!obj[prop] && isOverWrite){
                obj[prop] = propValue;
            }
            return this;
        },
        cf:function(obj, prop, propValue, isOverWrite){
            return this.cp(obj, prop, propValue, isOverWrite)
        },
        cp:function(obj, prop, propValue, isOverWrite){
            var undefined;
            if(isOverWrite === undefined){
                isOverWrite = true;
            }
            if(!obj.prototype[prop] || (obj.prototype[prop] && isOverWrite)){
                obj.prototype[prop] = propValue;
            }
            return this;
        },
        et:function(obj){

            arguments = Array.prototype.slice.call(arguments,1);
            if(!(arguments && arguments.length >= 1)) return obj;

            var cur = obj.prototype;
            for(var i in arguments){
                if(isNaN(i)) continue;
                if(typeof arguments[i] === 'object'){
                    for(var j in arguments[i]){
                        if(isNaN(i)) continue;
                        cur[j] = arguments[i][j];
                    }
                }
            };
            return this;
        }
    };
    return plugin;
}));