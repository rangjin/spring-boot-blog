jQuery.extend({
    getURLParam: function(strParamName){
        var strReturn = "";
        var strHref = window.location.href;
        var bFound=false;

        var cmpstring = strParamName + "=";
        var cmplen = cmpstring.length;

        if ( strHref.indexOf("?") > -1 ){
            var strQueryString = strHref.substr(strHref.indexOf("?")+1);
            var aQueryString = strQueryString.split("&");
            for ( var iParam = 0; iParam < aQueryString.length; iParam++ ){
                if (aQueryString[iParam].substr(0,cmplen)==cmpstring){
                    var aParam = aQueryString[iParam].split("=");
                    strReturn = aParam[1];
                    bFound=true;
                    break;
                }

            }
        }
        if (bFound==false) return null;
        return strReturn;
    }
});

let boardList = function (list) {
    list.forEach(post => {
        let html = '<a href="/post/detail/' + post.id + '">';
        html += '<h2 class="post-title">' + post.title + '</h2></a>';
        html += '<p class="post-meta">Posted at<br>Category <strong>' + post.categoryName + '</strong></p><hr>'

        $('.post-preview').append(html);
    });
}

let page = function (number, totalPages, first, last) {
    let start = (Math.floor(number / 10) * 10) + 1;
    let end = start + 9 < totalPages ? start + 9 : totalPages;

    if (first === true) {
        $('#move-to-previous').addClass('disabled');
    }
    $('#move-to-previous').children('a').attr("href", first ? '#' : "/?page=" + number);

    for (let i = start; i <= end; i++) {
        let html = '<li id="' + i + '" class="page-item"><a class="page-link" href="/?page=' + i + '">' + i + '</a></li>';
        $('#move-to-next').before(html);
    }
    $('#' + (number + 1)).addClass("active");

    if (last === true) {
        $('#move-to-next').addClass('disabled');
    }
    $('#move-to-next').children('a').attr("href", last ? '#' : "/?page=" + (number + 2));

    $('#move-to-last').children('a').attr("href", "/?page=" + totalPages);
}

let content = function (result) {
    $('#post-title').text(result.title);
    $('#post-updated-at').text(result.updatedAt);
    $('#post-category-name').text(result.categoryName);
    $('#code').val(result.content);

    setButton(result.id);

    var editor = CodeMirror.fromTextArea(document.getElementById('code'), {
        mode: "spell-checker",
        backdrop: "gfm",
        lineNumbers: false,
        matchBrackets: true,
        lineWrapping: true,
        theme: 'base16-light',
        extraKeys: {
            "Enter": "newlineAndIndentContinueMarkdownList"
        }
    });

    update(editor);
}

let deleteConfirm = function (id) {
    let result = confirm('정말로 게시글을 삭제하시겠습니까?');

    if (result) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/post/delete/' + id,
            success: function () {
                alert('게시글이 삭제되었습니다')
            }
        })

        location.href = "http://" + location.host;
    }
}

let setButton = function (id) {
    $('#post-edit-button').attr('href', '/post/edit/' + id);
    $('#post-delete-button').attr('onclick', 'deleteConfirm(' + id + ')');
}

