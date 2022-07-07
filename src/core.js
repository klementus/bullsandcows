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
    if(!checkSize(number)){return "size"}
    if(!checkRepeat(number)){return "repeat"}
    if(checkSize&&checkRepeat){return "correct"}
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

//основная проверка числа пользователя
function checkNumber(userNumber, botNumber){
    if(checkFull(userNumber, botNumber)){return "win"} 
    else {
    var bulls = checkBulls(userNumber, botNumber)
    var cows = checkCows(userNumber, botNumber)
    var result = [cows, bulls]
    }
}

//проверка на полное совпадение
function checkFull(number){
    
}

//проверка на быков
function checkBulls(number){
    
}

//проверка на коров
function checkCows(number){
    
}

#проверка на полное совпадение
                    if ($session.userNumber[0] == $session.botNumber[0]&&$session.userNumber[1] == $session.botNumber[1]&&$session.userNumber[2] == $session.botNumber[2]&&$session.userNumber[3] == $session.botNumber[3]) {
                        $reactions.answer("Ты выиграл! Хочешь еще раз?");
                        $reactions.transition("/Правила/Согласен?");
                    } 








