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
            #генерация случайного массива из 4 разных цифр (загаданное число)
            $session.array = [];
            var sameNumberFlag = false;
            for (var i = 0; $session.array.length < 4; i++) {
                $session.randomNumber = $jsapi.random(9) + 1;
                for(var y = 0; y<$session.array.length; y++){
                    if($session.array[y]==$session.randomNumber){
                        sameNumberFlag = true;    
                    } 
                }
                if(sameNumberFlag==false){$session.array.push($session.randomNumber);}
                sameNumberFlag = false;
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
            $session.userNumber = [];
            $session.cowsArray = [];
            $session.bullsArray = [];
            
            #конвертация числа пользователя в массив
            var str = String(num);
            for (var i = 0; i<str.length; i++){
                $session.userNumber.push(str[i])
            }
            
            #проверка числа пользователя на размер
            if($session.userNumber.length!=4){
                $reactions.answer("Пожалуйста введите 4-значное число с неповторяющимися цифрами");
            } 
            else {
            #проверка числа пользователя на повторения
                var sameNumberFlag = false;
                for (var i = 0; i<$session.userNumber.length; i++){
                    for (var y = 0; y<$session.userNumber.length; y++){
                        if($session.userNumber[i]==$session.userNumber[y]&&i!=y){
                            sameNumberFlag = true;
                        }
                    }
                }
                if(sameNumberFlag == true){
                    $reactions.answer("Пожалуйста введите 4-значное число с неповторяющимися цифрами");
                }
                else {
            #проверка на полное совпадение
                    if ($session.userNumber[0] == $session.botNumber[0]&&$session.userNumber[1] == $session.botNumber[1]&&$session.userNumber[2] == $session.botNumber[2]&&$session.userNumber[3] == $session.botNumber[3]) {
                        $reactions.answer("Ты выиграл! Хочешь еще раз?");
                        $reactions.transition("/Правила/Согласен?");
                    } 
                    else {
            #проверка на быков
                        for (var i = 0; i<$session.userNumber.length; i++){
                            if($session.userNumber[i] == tempArray[i]){
                            $session.bullsArray.push($session.userNumber[i])
                            tempArray[i]="x";
                            }
                        }
            #проверка на коров
                        for (var i = 0; i<$session.userNumber.length; i++){
                            for (var y = 0; y<$session.userNumber.length; y++){
                                if($session.userNumber[i] == tempArray[y]){
                                    $session.cowsArray.push($session.userNumber[i])
                                }
                            }
                        }
            $reactions.answer("Результат: {{$session.cowsArray.length}} «коров(ы)» ({{$session.cowsArray.length}} цифр(ы): ' {{$session.cowsArray}} ' — угаданы на неверных позициях) и {{$session.bullsArray.length}} «бык(и)» ({{$session.bullsArray.length}} цифр(ы): ' {{$session.bullsArray}} ' угаданы вплоть до позиции).");
            var $session.firstBlock = "";
            var $session.secondBlock = "";
            switch ($session.cowsArray.length) {
                case '0': $session.firstBlock = "ноль «коров» (ноль цифр"; break;
                case '1': $session.firstBlock = "одна «корова» (одна цифра: "; break;
                case '2': $session.firstBlock = "две «коровы» (две цифры:"; break;
                case '3': $session.firstBlock = "три «коровы» (три цифры:"; break;
                case '4': $session.firstBlock = "четыре «коровы» (четыре цифры:"; break;
            }
            switch ($session.bullsArray.length) {
                case '0': $session.secondBlock = "ноль «быков» (ноль цифр:"; break;
                case '1': $session.secondBlock = "один «бык» (одна цифра:"; break;
                case '2': $session.secondBlock = "два «быка» (две цифры:"; break;
                case '3': $session.secondBlock = "три «быка» (три цифры:"; break;
                case '4': $session.secondBlock = "четыре «быка» (четыре цифры:"; break;
            }
            $reactions.answer("Результат: {{$session.firstBlock}} «{{$session.cowsArray}}» — угаданы на неверных позициях) и {{$session.secondBlock}} «{{$session.bullsArray}}» угаданы вплоть до позиции).");
            
                    }
                }
            }
            

    state: NoMatch || noContext = true
        event!: noMatch
        random:
            a: Я не понял. Пожалуйста введите 4-значное число с неповторяющимися цифрами
            a: Что вы имеете в виду? Пожалуйста введите 4-значное число с неповторяющимися цифрами
            a: Ничего не пойму. Пожалуйста введите 4-значное число с неповторяющимися цифрами




