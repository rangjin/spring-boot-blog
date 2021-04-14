let auth = function (authenticated, noAuthenticated) {
    $(function () {
        if (getCookie('X-Auth-Token') !== '') {
            let html = '<li id="logout" class="nav-item"><a class="nav-link" href="/" onclick="logout()">Logout</a></li>';
            $('#category').after(html);

            authenticated();
        } else {
            let html = '<li id="login" class="nav-item"><a class="nav-link" href="/admin/login">Admin</a></li>';
            $('#category').after(html);

            noAuthenticated();
        }
    })
}

let logout = function () {
    document.cookie = 'X-Auth-Token=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';

    alert('로그아웃되었습니다');
}

let index = function () {
    $(function () {
        let authenticated = function () {
            let html = '<div class="create" style="float: right;"><a href="/post/create" class="btn-sm btn-primary">Create</a></div>';
            $('#post-content').before(html);
        }

        auth(authenticated, null);

        $.ajax({
            type: 'GET',
            url: '/api/v1/post/?page=' + ($.getURLParam("page") === null ? 1 : $.getURLParam("page")),
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function (result) {
                boardList(result.content);
                page(result.number, result.totalPages, result.first, result.last);
            },
            error: function (result) {
                console.log(result);
            }
        })
    });
}

jQuery.extend({
    getURLParam: function (strParamName) {
        let strReturn = "";
        let strHref = window.location.href;
        let bFound = false;

        let cmpString = strParamName + "=";
        let cmpLen = cmpString.length;

        if (strHref.indexOf("?") > -1){
            let strQueryString = strHref.substr(strHref.indexOf("?") + 1);
            let aQueryString = strQueryString.split("&");
            for (let iParam = 0; iParam < aQueryString.length; iParam++) {
                if (aQueryString[iParam].substr(0,cmpLen) === cmpString) {
                    let aParam = aQueryString[iParam].split("=");
                    strReturn = aParam[1];
                    bFound = true;

                    break;
                }

            }
        }
        if (bFound === false) return null;

        return strReturn;
    }
});

let boardList = function (list) {
    list.forEach(post => {
        let html = '<a href="/post/detail/' + post.id + '"><h2 class="post-title">' + post.title + '</h2></a>' +
            '<p class="post-meta">' +
            'Category <strong>' + post.categoryName + '</strong>' +
            '<br>' +
            'Posted at <strong>' + new Date(post.updatedAt).toLocaleString() + '</strong>' +
            '</p>' +
            '<hr>'

        $('.post-preview').append(html);
    });
}

let page = function (number, totalPages, first, last) {
    let start = (Math.floor(number / 10) * 10) + 1;
    let end = start + 9 < totalPages ? start + 9 : totalPages;

    let previous = $('#move-to-previous');
    let next = $('#move-to-next');

    $('#move-to-first').children('a').attr('href', location.pathname + "?page=1");

    if (first === true) {
        previous.addClass('disabled');
    }
    previous.children('a').attr("href", first ? '#' : location.pathname + "?page=" + number);

    for (let i = start; i <= end; i++) {
        let html = '<li id="' + i + '" class="page-item"><a class="page-link" href="' + location.pathname + '?page=' + i + '">' + i + '</a></li>';
        next.before(html);
    }
    $('#' + (number + 1)).addClass("active");

    if (last === true) {
        next.addClass('disabled');
    }
    next.children('a').attr("href", last ? '#' : location.pathname + "?page=" + (number + 2));

    $('#move-to-last').children('a').attr("href", location.pathname + "?page=" + totalPages);
}

let mdEditor = function () {
    new MdEditor('#mdeditor', {
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
    return {
        title: $('#title').val(),
        content: $('#mdeditor').val(),
        categoryId: $('#categoryId').val(),
        status: $('#status').val() === "" ? null : $('#status').val()
    };
}

let printValidationError = function (errors) {
    $('.is-invalid').removeClass('is-invalid');

    errors.forEach(function (error) {
        let field = $('#' + error.field);

        if (field.prop('tagName') === "SELECT") {
            field.addClass('is-invalid').find('.select-error').text(error.defaultMessage);
        } else {
            field.addClass('is-invalid').val("").attr('placeholder', error.defaultMessage);
        }
    })

    $('input[type=password]').val("");
}

let getCookie = function (name) {
    name = name + "=";
    let data = document.cookie.split(';');

    for(let i = 0; i < data.length; i++) {
        let item = data[i];

        //항목의 첫 글자가 공백인 경우에 두번째 위치부터 끝까지의 값을 item 변수에 대입
        while (item.charAt(0) === ' ') {
            item = item.substring(1);
        }

        // 항목의 내용 중에서 변수 name의 값을 가지는 위치가 0인경우 즉 처음위치
        if (item.indexOf(name) === 0) {
            return item.substring(name.length, item.length);
        }
    }

    return "";
}

let createPost = function () {
    $(function () {
        mdEditor();
        categorySelect();
        auth();
    })

    $('#post-create-button').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/api/v1/post/create',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(getPostData()),
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function (result) {
                if (result.validated === true) {
                    alert("글이 등록되었습니다");

                    location.href = "http://" + location.host + "/post/detail/" + result.data.id;
                } else {
                    printValidationError(result.data);
                }
            },
            error: function (result) {
                console.log(result);
            }
        })
    });
}

let postDetail = function () {
    $(function () {
        let authenticated = function () {
            let html = '<div style="float: right;">' +
                '<a id="post-edit-button" class="btn-sm btn-primary">Edit</a>' +
                '<a id="post-delete-button" class="btn-sm btn-primary">Delete</a>' +
                '</div>';

            $("#in").before(html);
        }

        auth(authenticated, null);

        $.ajax({
            type: 'GET',
            url: '/api/v1' + location.pathname,
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
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
        auth(null, null);
        categorySelect();

        $.ajax({
            type: 'GET',
            url: '/api/v1/post/detail/' + location.pathname.replace(/[^0-9]/g,''),
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
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
                if (result.validated === true) {
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
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function () {
                alert('게시글이 삭제되었습니다');

                location.href = "http://" + location.host;
            }
        })
    }
}

let postsFindByCategory = function () {
    $(function () {
        let authenticated = function () {
            let html = '<div class="create" style="float: right;"><a href="/post/create" class="btn-sm btn-primary">Create</a></div>';
            $('#post-content').before(html);
        }

        auth(authenticated, null);
    })

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
            page(result.number, result.totalPages, result.first, result.last);
        }
    })
}

let categoryList = function () {
    $.ajax({
        type: 'GET',
        url: '/api/v1/category',
        success: function (list) {
            let i = 1;

            list.forEach(category => {
                let html = '<tr onclick="location.href = \'http://' + location.host + '/post/category/' + category.id + '\'">' +
                    '<td>' + (i++) + '</td>' +
                    '<td>' + category.name + '</td>' +
                    '<td>' + category.postCnt + '</td>';

                html += getCookie('X-Auth-Token') === '' ? '</tr>' :
                    ('<td><a class = "auth btn-sm btn-primary" href="/category/edit/' + category.id + '">Edit</a></td>' +
                    '<td><a class = "auth btn-sm btn-primary" onclick="deleteCategory(' + category.id + ')">Delete</a></td></tr>')
                ;

                $('#categoryTableBody').append(html);
            })

            let authenticated = function () {
                let html = '<div class="auth" style="float: right; margin-bottom: 20px;"><a href="/category/create" class="btn-sm btn-primary">Create</a></div>';
                $('#categoryTable').before(html);

                html = '<td class="auth">수정</td><td class="auth">삭제</td>';
                $('#categoryTableHead').append(html);
            }

            auth(authenticated, null);
        }
    })
}

let getCategoryData = function () {
    return {
        name: $('#name').val()
    };
}

let categoryCreate = function () {
    $(function () {
        auth(null, null);
    })

    $('#category-create-button').on('click', function () {
        $.ajax({
            type: 'POST',
            url: '/api/v1/category/create',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(getCategoryData()),
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function (result) {
                if (result.validated === true) {
                    alert("카테고리가 생성되었습니다");

                    location.href = "http://" + location.host + "/category";
                } else {
                    printValidationError(result.data);
                }

                auth();
            }
        })
    })
}

let editCategory = function () {
    $(function () {
        auth();

        $.ajax({
            type: 'GET',
            url: '/api/v1/category/' + location.pathname.replace(/[^0-9]/g,''),
            success: function (result) {
                $(function () {
                    $('#name').val(result.name);
                })
            }
        })
    })

    $('#category-edit-button').on('click', function () {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/category/edit/' + location.pathname.replace(/[^0-9]/g,''),
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(getCategoryData()),
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function (result) {
                if (result.validated === true) {
                    alert("카테고리가 수정되었습니다");

                    location.href = "http://" + location.host + "/category";
                } else {
                    printValidationError(result.data);
                }

                auth();
            }
        })
    });
}

let deleteCategory = function (id) {
    let result = confirm("해당 카테고리와 게시물들을 전부 삭제하시겠습니까?");

    if (result) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/category/delete/' + id,
            beforeSend : function (xhr) {
                xhr.setRequestHeader("X-Auth-Token", getCookie('X-Auth-Token'));
            },
            success: function () {
                alert('카테고리와 게시물들이 전부 삭제되었습니다');

                location.href = 'http://' + location.host;
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}

let adminRegister = function () {
    $(function () {
        auth();
    })

    $('#admin-register-button').on('click', function () {
        let data = {
            username: $('#username').val(),
            password: $('#password').val(),
            rePassword: $('#rePassword').val()
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/admin/signup',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.validated === true) {
                    alert("관리자 회원 가입에 성공했습니다.");

                    location.href = "http://" + location.host + "/admin/login";
                } else {
                    printValidationError(result.data);
                }
            }
        })
    })
}

let adminLogin = function () {
    $(function () {
        auth();
    })

    $('#admin-login-button').on('click', function () {
        let data = {
            username: $('#username').val(),
            password: $('#password').val()
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/admin/signin',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.validated === true) {
                    alert("로그인에 성공했습니다.");

                    document.cookie = 'X-Auth-Token=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';

                    let date = new Date(Date.now() + 3600e3);
                    document.cookie = 'X-Auth-Token=' + result.data + "; path=/; expires=" + date.toGMTString() + ";";

                    location.href = "http://" + location.host;
                } else {
                    printValidationError(result.data);
                }
            },
            error: function (result) {
                console.log(result);
            }
        })
    })
}