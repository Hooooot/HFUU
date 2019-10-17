let jsonData = "{\"count\":8,\"msg\":\"\",\"code\":0,\"data\":[{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"},{\"age\":21,\"username\":\"whh\"}]}";
table.render({
    elem: '#data_table1' // 修改
    // ,url:'./json_test'
    ,data: jsonData
    ,even: true
    ,where: {
        "page" : "1"
        ,"limit": "30"
    }
    ,request: {
        pageName: "page"
        ,limitName:"limit"
    }
    ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
    ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        title: '提示'
        ,layEvent: 'LAYTABLE_TIPS'
        ,icon: 'layui-icon-tips'
    }]
    ,title: '用户数据表'
    ,cols: [[
        {type: 'checkbox', fixed: 'left'}
        ,{field:'age', title:'ID', width:80, fixed: 'left', unresize: true, sort: true}
        ,{field:'username', title:'用户名', width:120, edit: 'text'}
        // ,{field:'email', title:'邮箱', width:150, edit: 'text', templet: function(res){
        //         return '<em>'+ res.email +'</em>'
        //     }}
        // ,{field:'sex', title:'性别', width:80, edit: 'text', sort: true}
        // ,{field:'city', title:'城市', width:100}
        // ,{field:'sign', title:'签名'}
        // ,{field:'experience', title:'积分', width:80, sort: true}
        // ,{field:'ip', title:'IP', width:120}
        // ,{field:'logins', title:'登入次数', width:100, sort: true}
        // ,{field:'joinTime', title:'加入时间', width:120}
        ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
    ]]
    ,page: true
});

//头工具栏事件
table.on('toolbar(data_table1)', function(obj){ // 修改
    let checkStatus = table.checkStatus(obj.config.id);
    let data;
    switch(obj.event){
        case 'getCheckData':
            data = checkStatus.data;
            layer.alert(JSON.stringify(data));
            break;
        case 'getCheckLength':
            data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
            break;
        case 'isAll':
            layer.msg(checkStatus.isAll ? '全选': '未全选');
            break;

        //自定义头工具栏右侧图标 - 提示
        case 'LAYTABLE_TIPS':
            layer.alert('这是工具栏右侧自定义的一个图标按钮');
            break;
    }
});

//监听行工具事件
table.on('tool(data_table1)', function(obj){
    let data = obj.data;
    //console.log(obj)
    if(obj.event === 'del'){
        layer.confirm('真的删除行么', function(index){
            obj.del();
            layer.close(index);
        });
    } else if(obj.event === 'edit'){
        layer.prompt({
            formType: 2
            ,value: data.email
        }, function(value, index){
            obj.update({
                email: value
            });
            layer.close(index);
        });
    }
});