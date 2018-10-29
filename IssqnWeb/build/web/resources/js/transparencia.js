function app_handle_listing_horisontal_scroll(listing_obj) {
    //get table object   
    table_obj = $('.table', listing_obj);

    //get count fixed collumns params
    count_fixed_collumns = table_obj.attr('data-count-fixed-columns');

    if (count_fixed_collumns > 0)
    {
        //get wrapper object
        wrapper_obj = $('.table-scrollable', listing_obj);

        wrapper_left_margin = 0;

        table_collumns_width = new Array();
        table_collumns_margin = new Array();

        //calculate wrapper margin and fixed column width
        $('th', table_obj).each(function (index) {
            if (index < count_fixed_collumns)
            {
                wrapper_left_margin += $(this).outerWidth();
                table_collumns_width[index] = $(this).outerWidth();
            }
        });

        //calcualte margin for each column  
        $.each(table_collumns_width, function (key, value) {
            if (key === 0)
            {
                table_collumns_margin[key] = wrapper_left_margin;
            } else {
                next_margin = 0;
                $.each(table_collumns_width, function (key_next, value_next) {
                    if (key_next < key)
                    {
                        next_margin += value_next;
                    }
                });

                table_collumns_margin[key] = wrapper_left_margin - next_margin;
            }
        });

        //set wrapper margin               
        if (wrapper_left_margin > 0)
        {
            wrapper_obj.css('cssText', 'margin-left:' + wrapper_left_margin + 'px !important; width: auto');
        }

        //set position for fixed columns
        $('tr', table_obj).each(function () {

            //get current row height
            current_row_height = $(this).outerHeight();

            $('th,td', $(this)).each(function (index) {

                //set row height for all cells
                $(this).css('height', current_row_height);

                //set position 
                if (index < count_fixed_collumns)
                {
                    $(this).css('position', 'absolute')
                            .css('margin-left', '-' + table_collumns_margin[index] + 'px')
                            .css('width', table_collumns_width[index]);

                    $(this).addClass('table-fixed-cell');
                }
            });
        });
    }
}
var tam = 15;
var tam2 = 20;
var testeContraste = 0;
function mudaFonte(tipo, elemento) {


    if (tipo === "mais") {
        if (tam < 21)
            tam += 1;
        createCookie("fonte", tam, 365);
        if (document.getElementById("table1") !== null) {
            document.getElementById("table1").style.fontSize = tam + "px";
            document.getElementById("form1").style.fontSize = tam + "px";
            ajustarTela();
        } else {
            document.getElementById("content").style.fontSize = tam + "px";
        }
    } else
    if (tipo === "menos") {
        if (tam > 12)
            tam -= 1;
        createCookie("fonte", tam, 365);
        if (document.getElementById("table1") !== null) {
            document.getElementById("table1").style.fontSize = tam + "px";
            document.getElementById("form1").style.fontSize = tam + "px";
            ajustarTela();
        } else {
            document.getElementById("content").style.fontSize = tam + "px";
        }
    } else {
        tam = 15;
        createCookie("fonte", tam, 365);
        if (document.getElementById("table1") !== null) {
            document.getElementById("table1").style.fontSize = tam + "px";
            document.getElementById("form1").style.fontSize = tam + "px";
            $("#divMain").css('height', '88%');
            $("#divMain").css('width', '68%');
            $("#table1").css('width', '100%');
            $("#table1").css('font-size', '12px');
        } else {
            document.getElementById("content").style.fontSize = tam + "px";
        }
//
//        for (var i in document.getElementsByClassName('imagemtxt')) {
//            document.getElementsByClassName('imagemtxt')[i].style.fontSize = tam2 + "px";
//            document.getElementsByClassName('imagemtxt')[i].style.fontWeight = 900;
//        }

    }
}

function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    } else
        var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(";");
    for (var i = 0; i < ca.length; i++)
    {
        var c = ca[i];
        while (c.charAt(0) === " ") {
            c = c.substring(1, c.length);
            if (c.indexOf("contraste") > -1) {
                testeContraste = 1;
            }
            console.log(c);
        }

        if (testeContraste === 1) {
            console.log(testeContraste);
            $('body').addClass('contraste');
            createCookie("contraste", "contraste", 365);
            e.preventDefault();
        }

        if (c.indexOf(nameEQ) === 0)
            return c.substring(nameEQ.length, c.length);

    }
    return null;
}

function contraste(tipo, elemento) {

    if ($('body').attr('class') && $('body').attr('class').indexOf("contraste") > -1) {
        $('body').removeClass('contraste');
        eraseCookie('contraste');
//        elemento.preventDefault();
        return false;
    } else {
        $('body').addClass('contraste');
        createCookie("contraste", "contraste", 365);
//        elemento.preventDefault();
        return false;
    }
}

function eraseCookie(name)
{
    createCookie(name, '', -1);
    console.log(name);
}

function mudarLayout(tipo, elemento) {

    if ($('body').attr('class') && $('body').attr('class').indexOf("lista") > -1) {
        $('body').removeClass('lista');
        $('body').addClass('coluna');
        e.preventDefault();
        return false;
    } else {
        $('body').removeClass('coluna');
        $('body').addClass('lista');
        e.preventDefault();
        return false;
    }
}



function ajustarTela() {
    var altura = "88%";
    var valor_altura = $("#table1").height() + 260;
    var valor_largura = $("#table1").width() + 120;

    if (valor_largura < 400) {
        valor_altura = valor_altura + 460;
        altura = valor_altura + 'px';
    } else {
        if (valor_altura > 800) {
            altura = valor_altura + 'px';
        }
    }
    console.log("valor " + valor_largura);
    $("#divMain").css('height', altura);
    $("#divMain").css('width', valor_largura + 'px');
    valor_largura = $("#divMain").width();
    $("#table1").css('width', valor_largura + 'px');
}
        