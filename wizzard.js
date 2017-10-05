use test

var map = function() {
	var components = this.components;
	var AvailableFor = this.AvailableFor;

	for( i of components){
		if ( i == "V" &&  components.lenght()==1){
			for(i in AvailableFor){
				if(i.wizard <= 4){
					emit(this['_id'],{SpellName :this['SpellName']});
				}
			}
		}
	}
}
var reduce = function(){

}

db.sorts.mapReduce(map,reduce,{out : 'sorts_wizard_lvl4_V'});
db.sorts_wizard.find();
