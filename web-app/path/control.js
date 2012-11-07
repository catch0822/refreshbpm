// Path Navigator effect
function PieMenuInit(){
    $('#outer_container').PieMenu({
        'starting_angel':320,
        'angel_difference' : 90,
        'radius': 100
    });
}

function reset(){
    if($(".menu_button").hasClass('btn-rotate'))
        $(".menu_button").trigger('click');

    $("#info").fadeIn("slow").fadeOut("slow");
    PieMenuInit();
}


$(document).ready(function(){

    // Path navigator control
    $("#submit_button").click(function() {
        reset();
    });
    $( "#outer_container" ).draggable();
    PieMenuInit();

});