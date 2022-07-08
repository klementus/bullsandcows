require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common
    
require: core.js


theme: /

    state: Правила
        q!: $regex</start>
        intent!: /Давай поиграем
        a: Игра «Быки и коровы». Я загадаю четырехзначное число, ты будешь отгадывать. Если угадаешь цифру и место, то это бык. Если только цифру, но не на своем месте, то это корова. Начнём?
        go!: /Правила/Согласен?

        state: Согласен?

            state: Да
                intent: /Согласие
                go!: /Игра

            state: Нет
                intent: /Несогласие
                a: Ну и ладно! Если передумаешь - скажи "давай поиграем"

    state: Игра
        script:
            #генерация случайного массива из 4 разных цифр (загаданное число)
            $session.botNumber = botThinkNumber();
            #переход в стейт /Проверка
            $reactions.transition("/Проверка");
            

    state: Проверка
        intent: /Число
        script:
            #инициализация ввода пользователя
            $session.userNumber = userInput($parseTree);
            
            #проверка числа пользователя на корректность
            switch (isCorrect($session.userNumber)) {
                case 'size': $reactions.answer("Ошибка. Пожалуйста введите 4-значное число с неповторяющимися цифрами. Неверное количество цифр"); break;
                case 'repeat': $reactions.answer("Ошибка. Пожалуйста введите 4-значное число с неповторяющимися цифрами. Повторяющиеся цифры"); break;
                case 'correct': 
                    $session.bullsArray = checkBulls($session.userNumber, $session.botNumber);
                    $session.cowsArray = checkCows($session.userNumber, $session.botNumber);
                    break;
            }
            
            if(isCorrect($session.userNumber)=="correct"){
                if($session.bullsArray.length==4){
                    $reactions.answer("Ты выиграл! Хочешь еще раз?");
                    $reactions.transition("/Правила/Согласен?");
                } 
                else {
                    $session.result = formTheAnswer($session.bullsArray, $session.cowsArray)
                    $reactions.answer("Результат: {{$session.result}}");
                }
            }
            
            
         
            
            #TEMP
            $reactions.answer("$session.botNumber {{$session.botNumber}}");
            $reactions.answer("$session.userNumber {{$session.userNumber}}");
            $reactions.answer("$session.bullsArray {{$session.bullsArray}}");
            $reactions.answer("$session.cowsArray {{$session.cowsArray}}");
            #TEMP
            
            
            

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял.
            a: Что вы имеете в виду? 
            a: Ничего не пойму.

    state: EndGame
        intent!: /endThisGame
        a: Очень жаль! Если передумаешь — скажи "давай поиграем"



