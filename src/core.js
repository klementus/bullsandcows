//генерация случайного массива из 4 разных цифр (загаданное число)
function botThinkNumber(){
    var botNumber = []
    var sameNumberFlag = false;
    for (var i = 0; botNumber.length < 4; i++) {
        var randomNumber = $jsapi.random(9) + 1;
        for(var y = 0; y<botNumber.length; y++){
            if(botNumber[y]==randomNumber){
                sameNumberFlag = true;    
            } 
        }
        if(sameNumberFlag==false){botNumber.push(randomNumber);}
        sameNumberFlag = false;
    }
    return botNumber
}

//парсинг ввода пользователя
function userInput(parseTree){
    var num = parseTree._Number;
    num = intToArray(num);
    
    return num
}

//конвертация int в массив
function intToArray(number){
    var str = String(number);
    var arrayNumber = [];
    for (var i = 0; i<str.length; i++){
        arrayNumber.push(str[i])
    }
    return arrayNumber
}

//проверка числа пользователя на корректность
function isCorrect(number){
    if(checkSize(number)&&checkRepeat(number)){return true} else {return false}
}

//проверка числа пользователя на размер
function checkSize(number){
    if(number.length==4){
        return true
    } 
    else{ 
        return false 
    }
}

//проверка числа пользователя на повторения
function checkRepeat(number){
    var unique = true;
    for (var i = 0; i<number.length; i++){
        for (var y = 0; y<number.length; y++){
            if(number[i]==number[y]&&i!=y){
                unique = false;
            }
        }
    }
    if(unique == true){return true} else {return false}
}

















