/*常量*/
var CONSTANT = {
		DATA_TABLES : {
			DEFAULT_OPTION : { //DataTables初始化选项
				language: {
					"sProcessing":   "处理中...",
					"sLengthMenu":   "每页 _MENU_ 项",
					"sZeroRecords":  "没有匹配结果",
					"sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
					"sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
					"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
					"sInfoPostFix":  "",
					"sSearch":       "搜索:",
					"sUrl":          "",
					"sEmptyTable":     "表中数据为空",
					"sLoadingRecords": "载入中...",
					"sInfoThousands":  ",",
					"oPaginate": {
						"sFirst":    "首页",
						"sPrevious": "上页",
						"sNext":     "下页",
						"sLast":     "末页",
						"sJump":     "跳转"
					},
					"oAria": {
						"sSortAscending":  ": 以升序排列此列",
						"sSortDescending": ": 以降序排列此列"
					}
				},		
			    "paging": "false", 
			       lengthMenu: [
			            [ 10, 25, 50, -1],
			            [ '显示10条', '显示25条', '显示50条', '全显示 ' ]
			        ],
			        
			        dom: 'Bfrtip',
			        buttons: [
			            'pageLength',
			            {
			                extend:    'copyHtml5',
			                text:      '<i class="fa fa-files-o"></i>',
			                titleAttr: 'Copy'
			            },
			            {
			                extend:    'excelHtml5',
			                text:      '<i class="fa fa-file-excel-o"></i>',
			                titleAttr: 'Excel'
			            },
			            {
			                extend:    'csvHtml5',
			                text:      '<i class="fa fa-file-text-o"></i>',
			                titleAttr: 'CSV',
			                "bShowAll": true,
			                exportOptions: {
			                    columns: ':visible'
			                }
			            },			            
			            {
			                extend:    'pdfHtml5',
			                text:      '<i class="fa fa-file-pdf-o"></i>',
			                titleAttr: 'PDF'
			            }

			        ],
			        columnDefs: [ {
			        	targets: -1,
			            visible: false
			        } ],
			        
			     select : true,
                stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
                order: [],			//取消默认排序查询,否则复选框一列会出现小箭头
                processing: false,	//隐藏加载提示,自行处理
                serverSide: true,	//启用服务器端分页
                searching: false,	//禁用原生搜索
                renderer: "bootstrap",
                "sPaginationType": "bootstrap",
                pagingType: "simple_numbers"
                
			},
			COLUMN: {
                CHECKBOX: {	//复选框单元格
                    className: "td-checkbox",
                    orderable: false,
                    width: "30px",
                    data: null,
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" class="iCheck">';
                    }
                }
            },
            RENDER: {	
                ELLIPSIS: function (data, type, row, meta) {
                	data = data||"";
                	return '<span title="' + data + '">' + data + '</span>';
                }
            }
		}
};