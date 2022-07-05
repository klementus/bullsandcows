require: slotfilling/slotFilling.sc
  module = sys.zb-common

require: common.js
    module = sys.zb-common

theme: /

    state: Правила
        q!: $regex</start>
        intent!: /Давай поиграем
        a: Игра больше-меньше. Загадаю число от 0 до 100, ты будешь отгадывать. Начнём?
        go!: /Правила/Согласен?

        state: Согласен?

            state: Да
                intent: /Согласие
                go!: /Игра

            state: Нет
                intent: /Несогласие
                a: Ну и ладно! Если передумаешь - скажи "давай поиграем"

    state: Игра
        # сгенерируем случайное число и перейдем в стейт /Проверка
        script:
            $session.array = [];
        
            for (var i = 0; i < 4; i++) {
                $session.randomNumber = $jsapi.random(9) + 1;
                $session.array.push($session.randomNumber);
            }
            
            $session.botNumber = $session.array;
            #TEMP
            $reactions.answer("Загадано {{$session.botNumber}}");
            #TEMP
            $reactions.transition("/Проверка");

    state: Проверка
        intent: /Число
        script:
            # сохраняем введенное пользователем число
            var num = $parseTree._Number;
            var str = String(num);
            $session.cows = 0;
            $session.bulls = 0;
            $session.userNumber = [];
            
            for (var i = 0; i<str.length; i++){
                $session.userNumber.push(str[i])
            }
            
            #TEMP
            $reactions.answer("Вы ввели {{$session.userNumber}}");
            #TEMP
            
            

            # проверяем угадал ли пользователь загаданное число и выводим соответствующую реакцию
            if ($session.userNumber[0] == $session.botNumber[0]&&$session.userNumber[1] == $session.botNumber[1]&&$session.userNumber[2] == $session.botNumber[2]&&$session.userNumber[3] == $session.botNumber[3]) {
                $reactions.answer("Ты выиграл! Хочешь еще раз?");
                $reactions.transition("/Правила/Согласен?");
            } 
            else {
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
