function URLPath(msg){
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    if(msg==undefined){
        return projectName;
    }
    return projectName+msg;
}

