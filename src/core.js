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

//проверка на быков
function checkBulls(userNumber, botNumber){
    var bullsArray = [];
    for (var i = 0; i<userNumber.length; i++){
        if(userNumber[i] == botNumber[i]){
        bullsArray.push(userNumber[i])
        }
    }
    return bullsArray
}

//проверка на коров
function checkCows(userNumber, botNumber){
    var cowsArray = [];
    for (var i = 0; i<userNumber.length; i++){
        for (var y = 0; y<userNumber.length; y++){
            if(userNumber[i] == botNumber[y]){
                cowsArray.push(userNumber[i])
            }
        }
    }
    return cowsArray
}

//формирования ответа на число пользователя
function formTheAnswer(bullsArray, cowsArray){
    var answerCows = formCows(cowsArray);
    var answerBulls = formBulls(bullsArray);
    var result = answerCows+answerBulls
    
    return result
}

//формирование ответа на число коров
function formCows(cowsArray){
    var answerCows = "";
    
    switch(String(cowsArray.length)){
        case "0": answerCows = "ноль «коров» и ";break;
        case "1": answerCows = "одна «корова» (одна цифра: «"+cowsArray[0]+"» — угадана на неверной позиции) и ";break;
        case "2": answerCows = "две «коровы» (две цифры: «"+cowsArray[0]+"» и «"+cowsArray[1]+"» — угаданы на неверной позиции) и ";break;
        case "3": answerCows = "три «коровы» (три цифры: «"+cowsArray[0]+"», «"+cowsArray[1]+"» и «"+cowsArray[2]+"» — угаданы на неверной позиции) и ";break;
        case "4": answerCows = "четыре «коровы» (четыре цифры: «"+cowsArray[0]+"», «"+cowsArray[1]+"», «"+cowsArray[2]+"» и «"+cowsArray[3]+"» — угаданы на неверной позиции) и ";break;
    }
    return answerCows
}

//формирование ответа на число быков
function formBulls(bullsArray){
    var answerBulls = "";
    
    switch(String(bullsArray.length)){
        case "0": answerBulls = "ноль «быков»";break;
        case "1": answerBulls = "один «бык» (одна цифра «"+bullsArray[0]+"» угадана вплоть до позиции).";break;
        case "2": answerBulls = "два «быка» (две цифры «"+bullsArray[0]+"» и «"+bullsArray[1]+"» угаданы вплоть до позиции).";break;
        case "3": answerBulls = "три «быка» (три цифры «"+bullsArray[0]+"», «"+bullsArray[1]+"» и «"+bullsArray[2]+"» угаданы вплоть до позиции).";break; //хм, а разве может быть три быка? оставлю на всякий случай
    }
    return answerBulls
}







