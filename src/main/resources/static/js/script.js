$(document).ready(function(){
    index = 0;
    var wrapper = $('.morePlayers');

    $("#addPlayer").click(function (){
        var fieldHTML =
            '        <h3>Pievienot '+(index+1)+' spēlētāju</h3>' +
            '        <div class="form-group">\n' +
            '            <input class="form-control" type="text" id="players'+index+'.name" name="players['+index+'].name" placeholder="Spēlētāja vārds"/>\n' +
            '        </div>\n' +
            '        <div class="form-group">\n' +
            '            <input class="form-control" type="text" id="players'+index+'.surname" name="players['+index+'].surname" placeholder="Spēlētāja uzvārds"/>\n' +
            '        </div>\n' +
            '        <div class="form-group">\n' +
            '            <input class="form-control" type="text" id="players'+index+'.position" name="players['+index+'].position" placeholder="Spēlētāja pozīcija"/>\n' +
            '        </div>';
        $(wrapper).append(fieldHTML);
        index++;
    });
});