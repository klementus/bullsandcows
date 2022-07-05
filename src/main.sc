require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common

theme: /

    state: Правила
        q!: $regex</start>
        intent!: /Давай поиграем
        a: Игра быки и коровы. Я загадаю четырехзначное число, ты будешь отгадывать. Если угадаешь цифру и место, то это бык. Если только цифру, но не на своем месте, то это корова. Начнём?
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
            # генерация случайного массива из 4 чисел (загаданное число)
            $session.array = [];
            for (var i = 0; i < 4; i++) {
                $session.randomNumber = $jsapi.random(9) + 1;
                $session.array.push($session.randomNumber);
            }
            $session.botNumber = $session.array;
            
            #переход в стейт /Проверка
            $reactions.transition("/Проверка");

    state: Проверка
        intent: /Число
        script:
            #парсинг ввода пользователя
            var num = $parseTree._Number;
            #инициализация переменных
            var tempArray = $session.botNumber.slice();
            $session.cows = 0;
            $session.bulls = 0;
            $session.userNumber = [];
            
            #конвертация числа пользователя в массив
            var str = String(num);
            for (var i = 0; i<str.length; i++){
                $session.userNumber.push(str[i])
            }
            
            # проверка числа пользователя
            if ($session.userNumber[0] == $session.botNumber[0]&&$session.userNumber[1] == $session.botNumber[1]&&$session.userNumber[2] == $session.botNumber[2]&&$session.userNumber[3] == $session.botNumber[3]) {
                $reactions.answer("Ты выиграл! Хочешь еще раз?");
                $reactions.transition("/Правила/Согласен?");
            } 
            else {
            #проверка на быков
                for (var i = 0; i<$session.userNumber.length; i++){
                    if($session.userNumber[i] == tempArray[i]){
                    $session.bulls+=1;
                    tempArray[i]="x";
                    }
                }
            #проверка на коров
                for (var i = 0; i<$session.userNumber.length; i++){
                    for (var y = 0; y<$session.userNumber.length; y++){
                        if($session.userNumber[i] == tempArray[y]){
                            $session.cows+=1;
                        }
                    }
                }
                
            
            #TEMP
            $reactions.answer("$session.botNumber {{$session.botNumber}}");
            $reactions.answer("$session.userNumber {{$session.userNumber}}");
            $session.tempArray = tempArray;
            $reactions.answer("$session.tempArray {{$session.tempArray}}");
            #TEMP
            
            
                
                
            $reactions.answer("Коров: {{$session.cows}}, быков: {{$session.bulls}}");
            }
            
            
            
            # else {
            #     if ($session.userNumber[0] == $session.botNumber[0]){bulls+=1}else{
            #         for (var i = 0; i<$session.botNumber.length; i++){
            #         if($session.userNumber[0] == $session.botNumber[i]){bulls+=1};break)
            #         }
            #     }
            # }
            
            #if ($session.arr < $session.number)
            #$reactions.answer(selectRandomArg(["Мое число больше!", "Бери выше", "Попробуй число больше"]));
            #else $reactions.answer(selectRandomArg(["Мое число меньше!", "Подсказка: число меньше", "Дам тебе еще одну попытку! Мое число меньше."]));

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял
            a: Что вы имеете в виду?
            a: Ничего не пойму
