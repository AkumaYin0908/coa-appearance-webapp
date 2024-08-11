export default class DialogDetails{

    constructor(title,text,icon,confirmButtonText="Yes",cancelButtonText="Cancel"){
        this._title = title;
        this._text=text;
        this._icon = icon;
        this._confirmButtonText = confirmButtonText;
        this._cancelButtonText = cancelButtonText;
    }

    get title(){
        return this._title;
    }

    get text(){
        return this._text;
    }

    get icon(){
        return this._icon;
    }

    get confirmButtonText(){
        return this._confirmButtonText;
    }
    
    get cancelButtonText(){
        return this._cancelButtonText;
    }
    set title(title){
        this._title = title;
    }

    set text(text){
        this._text=text;
    }

    set icon(icon){
        this._icon = icon;
    }

    set confirmButtonText(confirmButtonText){
        this._confirmButtonText = confirmButtonText;
    }

    set cancelButtonText(cancelButtonText){
        this._cancelButtonText = cancelButtonText;
    }

}