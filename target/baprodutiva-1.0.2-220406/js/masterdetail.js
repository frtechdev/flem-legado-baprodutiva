var browser = navigator.appName;
var listTables = new ListTables();

function getValueSelectBox(id){
    var selecao = document.getElementById(id);
    return selecao[selecao.selectedIndex].value;
}

function getTextSelectBox(id){
    var selecao = document.getElementById(id);
    return selecao[selecao.selectedIndex].text;
}

function getText(id){
    return document.getElementById(id).text;
}

function getValue(id){
    return document.getElementById(id).value;
}

function Line(id, text, table){
    this.id = id;
    this.table = table;
    this.text = text;
    this.table.addLine(this);     
    
    this.url = function() {
        var link = document.createElement('a');
        
        link.setAttribute('id', this.id);              
        link.className = 'contrast';
        
        var img = document.createElement('img');
        img.setAttribute('src', 'img/delete.png');
        img.setAttribute('border', '0');
        
        
        if(browser == 'Microsoft Internet Explorer') {
            img.onclick = function () {                                
                remove(id,table.id);
            };              
        }
        
        else {
            img.setAttribute('onClick', 'javascript:remove(\'' + id +'\',\'' + table.id + '\')');              
        }
        link.appendChild(img);
        return link;
    }
    
}

function Table(id){
    this.id = id; 
    this.lines = new Array();
    
    listTables.addTable(this);
    
    this.getLineById = function(id){     
        for(i in this.lines){                                
            if(this.lines[i].id == id){
                return this.lines[i];
            }
        }							
    }
    
    this.addLine =  function(line){    
        line.table = this;
        this.lines.push(line);
    }
    
    this.removeLine = function(line){
        
        for(i in this.lines){							
            if(this.lines[i].id == line.id){								
                this.lines.splice(i, 1);
            }
        }
    }
}

function ListTables(){    							
    this.tables = new Array();
    this.addTable =  function(table){        
        this.tables.push(table);
    }
    
    this.getTableById = function(id){                            
        for(i in this.tables){
            if(this.tables[i].id == id){                                    
                return this.tables[i];
            }
        }							
    }
}

//default behaviour
function add(lineId, text, tableId){  
    var table = listTables.getTableById(tableId);
    if(table == null){        
        table = new Table(tableId);
    }
    
    var linha = table.getLineById(lineId);
    if(linha == null){
        linha = new Line(lineId, text, table);            
    }
    paint(table); 
}

function remove(lineId, tableId){ 
    var table = listTables.getTableById(tableId);
    var line = table.getLineById(lineId);
    table.removeLine(line);
    paint(table);    
}

//convention: result table name = table id + "Lines"
function paint(table){
    
    var destiny = table.id + 'Lines';
    
    var cellFuncs = [ 
        function(data) {
            return data;
        }
    ];  
    
    DWRUtil.removeAllRows(destiny);
    for(i in table.lines){
        
        var line = table.lines[i];        
        
        DWRUtil.addRows(destiny, cellFuncs, [line.text, line.url], 
            {				  
                rowCreator : function(options) {
                    var tr = document.createElement("tr"); 
                    
                    if(browser == 'Microsoft Internet Explorer') {
                        tr.id = line.id; 
                    }
                    
                    else {
                        tr.setAttribute('id', line.id); 
                    }
                    
                    return tr; 
                }//fim rowCreator
            })//fim addRows
        }
    }
    
    function serializeIn(tableId, destinyFieldId){

        var table = listTables.getTableById(tableId);
        var destinyField = document.getElementById(destinyFieldId);
        
        if(table != null){
            for(i in table.lines){
                var line = table.lines[i];
                
                if(destinyField.value == null || destinyField.value == ''){
                    destinyField.value = line.id;        
                }
                
                else{
                    destinyField.value = destinyField.value + ';' +line.id;        
                }
            }
            return true;            
        }        
        return false;
    }
    