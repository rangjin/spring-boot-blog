let index = function () {
    $(function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/post/?page=' + ($.getURLParam("page") === null ? 1 : $.getURLParam("page")),
            success: function (result) {
                boardList(result.content);
                page(result.number, result.totalPages, result.first, result.last)
            }
        })
    });
}

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
        html += '<p class="post-meta">Category <strong>' + post.categoryName + '</strong><br>Posted at <strong>' + new Date(post.updatedAt).toLocaleString() + '</strong></p><hr>'

        $('.post-preview').append(html);
    });
}

let page = function (number, totalPages, first, last) {
    let start = (Math.floor(number / 10) * 10) + 1;
    let end = start + 9 < totalPages ? start + 9 : totalPages;

    $('#move-to-first').children('a').attr('href', location.pathname + "?page=1");

    if (first === true) {
        $('#move-to-previous').addClass('disabled');
    }
    $('#move-to-previous').children('a').attr("href", first ? '#' : location.pathname + "?page=" + number);

    for (let i = start; i <= end; i++) {
        let html = '<li id="' + i + '" class="page-item"><a class="page-link" href="' + location.pathname + '?page=' + i + '">' + i + '</a></li>';
        $('#move-to-next').before(html);
    }
    $('#' + (number + 1)).addClass("active");

    if (last === true) {
        $('#move-to-next').addClass('disabled');
    }
    $('#move-to-next').children('a').attr("href", last ? '#' : location.pathname + "?page=" + (number + 2));

    $('#move-to-last').children('a').attr("href", location.pathname + "?page=" + totalPages);
}

let mdEditor = function () {
    let md = new MdEditor('#mdeditor', {
        uploader: 'http://local.dev/Lab/MdEditor/app/upload.php',
        preview: true,
        images: [
        ]
    });
}

let categorySelect = function () {
    $.ajax({
        type: 'GET',
        url: '/api/v1/category',
        success: function (results) {
            results.forEach(result => {
                let html = '<option value="' + result.id + '">' + result.name + '</option>';

                $('#categoryId').append(html);
            })
        }
    });
}

let getPostData = function () {
    let data = {
        title : $('#title').val(),
        content : $('#mdeditor').val(),
        categoryId : $('#categoryId').val(),
        status : $('#status').val() === "" ? null : $('#status').val()
    }

    return data;
}

let printValidationError = function (errors) {
    $('.is-invalid').removeClass('is-invalid');

    errors.forEach(function (error) {
        $('#' + error.field).addClass('is-invalid').attr('placeholder', error.defaultMessage);
    })
}

let createPost = function () {
    $(function () {
        mdEditor();
        categorySelect();
    })

    $('#post-create-button').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/api/v1/post/create',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(getPostData()),
            success: function (result) {
                if (result.validated == true) {
                    alert("글이 등록되었습니다");

                    location.href = "http://" + location.host + "/post/detail/" + result.data.id;
                } else {
                    printValidationError(result.data);
                }
            }
        })
    });
}

let postDetail = function () {
    $(function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1' + location.pathname,
            success: function (result) {
                $('#mdeditor').val(result.content);
                mdEditor();

                $('#out').html($('.mdeditor_render'));
                $('#in').remove();

                $('#post-title').text(result.title);
                $('#post-updated-at').text(new Date(result.updatedAt).toLocaleString());
                $('#post-category-name').text(result.categoryName);

                $('#post-edit-button').attr('href', '/post/edit/' + result.id);
                $('#post-delete-button').attr('onclick', 'deletePost(' + result.id + ')');
            }
        })
    })
}

let editPost = function () {
    $(function () {
        categorySelect();
        $.ajax({
            type: 'GET',
            url: '/api/v1/post/detail/' + location.pathname.replace(/[^0-9]/g,''),
            success: function (result) {
                $(function () {
                    $('#title').val(result.title);
                    $('#categoryId').find($('option[value=' + result.categoryId + ']').attr('selected', 'selected'));
                    $('#status').find($('option[value=' + result.status + ']')).attr('selected', 'selected');
                    $('#mdeditor').val(result.content);

                    mdEditor();
                })
            }
        })
    })

    $('#post-edit-button').on('click', function () {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/post/edit/' + location.pathname.replace(/[^0-9]/g,''),
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(getPostData()),
            success: function (result) {
                if (result.validated == true) {
                    alert("글이 수정되었습니다");

                    location.href = "http://" + location.host + "/post/detail/" + result.data.id;
                } else {
                    printValidationError(result.data);
                }
            }
        })
    });
}

let deletePost = function (id) {
    let result = confirm('정말로 게시글을 삭제하시겠습니까?');

    if (result) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/post/delete/' + id,
            success: function () {
                alert('게시글이 삭제되었습니다');
            }
        })

        location.href = "http://" + location.host;
    }
}

let postsFindByCategory = function () {
    $.ajax({
        type: 'GET',
        url: '/api/v1/category/' + location.pathname.replace(/[^0-9]/g,''),
        success: function (result) {
            $('#categoryInfo').text(result.name + "(" + result.postCnt + ")");
        }
    })

    $.ajax({
        type: 'GET',
        url: '/api/v1' + location.pathname + '/?page=' + ($.getURLParam("page") === null ? 1 : $.getURLParam("page")),
        success: function (result) {
            boardList(result.content);
            page(result.number, result.totalPages, result.first, result.last)
        }
    })
}
