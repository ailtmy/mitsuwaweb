$(document).ready(function(){

	$('[id^="customer"]').select2({
        placeholder: "選択して下さい",
        allowClear: true
    });

//	$('.select2').select2({
//		placeholder: '選択してください	',
//		allowClear: true
//	}).on('select2:select', function(){
//		var gid = $(this).val();
//		var idname = $(this).attr("id");
//		$.ajax({
//			type: "GET",
//			url: "/soft/hozon/addr",
//			data: {id: gid},
//			dataType: "text",
//		})
//		.done(function(data){
//			$("#" + idname).next().next().next().html(data);
//		})
//	});

	//メールアドレス追加
	$('#mail_new').click(function(){
		$('.mail_hyouji:last').clone().appendTo('.new_mailwrap');
	});

	//メールアドレス削除
	$('#mail_remove').click(function(){
		if($('.mail_hyouji').length != 1){
			$('.mail_hyouji:last-child').remove();
		}
	});

	//電話追加
	$('#tel_new').click(function(){
		$('.tel_hyouji:last').clone().appendTo('.new_telwrap');
	});

	//電話削除
	$('#tel_remove').click(function(){
		if($('.tel_hyouji').length != 1){
			$('.tel_hyouji:last-child').remove();
		}
	});

	//未失効物件追加
	$('#mishikko_bukken').click(function(){
		$('.mishikko_bukken_hyouji:last').clone().appendTo('.mishikko_bukken_wrap');
	});

	//未失効物件削除
	$('#mishikko_bukken_remove').click(function(){
		$('.mishikko_bukken_hyouji:last-child').remove();
	});

	//建物附属建物追加
	$('#tatemono_fuzoku_new').click(function(){
		$(".tatemono_fuzokuhyoji:last").clone().appendTo('.tatemono_new_fuzokuwrap');
	});

	//建物附属建物削除
	$('#tatemono_fuzoku_remove').click(function(){
//		if($('.tatemono_fuzokuhyoji').length != 1){
			$('.tatemono_fuzokuhyoji:last-child').remove();
//		}
	});

	//敷地権追加
	$('#shikichi_new').click(function(){
		$(".shikichihyoji:last").clone().appendTo('.shikichiwrap');
	});

	//敷地権削除
	$('#shikichi_remove').click(function(){
		$('.shikichihyoji:last-child').remove();
	});

	//権利者追加
	$('#kenrisya_add').click(function(){
		$('.kenrisya_hyouji:last').clone().appendTo('.kenrisya_wrap');
	});

	//権利者削除
	$('#kenrisya_remove').click(function(){
		if($('.kenrisya_hyouji').length != 1){
			$('.kenrisya_hyouji:last-child').remove();
		}
	});

	//物件追加
	$('#bukken_add').click(function(){
		$('.shinseiBukken_hyouji:last').clone().appendTo('.shinseiBukken_wrap');
	});

	//物件削除
	$('#bukken_remove').click(function(){
		if($('.shinseiBukken_hyouji').length != 1){
			$('.shinseiBukken_hyouji:last-child').remove();
		}
	});

	//書類追加
	$('#syorui_add').click(function(){
		$('.syorui_hyouji:last').clone().appendTo('.syorui_wrap');
	});

	//書類削除
	$('#syorui_remove').click(function(){
		if($('.syorui_hyouji').length != 1){
			$('.syorui_hyouji:last-child').remove();
		}
	});

	//住所セレクトオプション追加
//	$(function(){
//		$('[id^="customer"]').change(function(){
//			var idname = $(this).attr("id");
//			$.ajax({
//				type: "GET",
//				url: "/soft/hozon/addr",
//				data: {id: $("#" + idname).val()},
//				dataType: "text",
//				success: function(msg){
//					$("#" + idname).next('select').html(msg);
//				}
//			});
//		});
//	});

	//住所追加要素イベント発火
//	$(document).on("change", '[id^="customer"]', function(){
//		var idname = $(this).attr("id");
//		$.ajax({
//			type: "GET",
//			url: "/soft/hozon/addr",
//			data: {id: $("#" + idname).val()},
//			dataType: "text",
//			success: function(msg){
//				$("#" + idname).next().next('select').html(msg);
//			}
//		});
//	});

	//住所追加要素イベント発火
	$(document).on("change", '[id^="customer"]', function(){
		var idname = $(this).attr("id");
		$.ajax({
			type: "GET",
			url: "/soft/hozon/addr",
			data: {id: $("#" + idname).val()},
			dataType: "text",
//			success: function(msg){
//				$("#" + idname).next().next('select').html(msg);
//			}
		})
		.done(function(data){
			$("#" + idname).next().next().next().html(data);
		});
	});

	//代表者追加要素イベント発火
	$(document).on("change", '[id^="customer"]', function(){
		var idname = $(this).attr("id");
		$.ajax({
			type: "GET",
			url: "/soft/hozon/daihyo",
			data: {id: $("#" + idname).val()},
			dataType: "text",
//			success: function(msg){
//				$("#" + idname).next().next().next().next('select').html(msg);
//			}
		})
//		console.log($("#" + idname).next().next().next().next().next().prop("tagName"));
		.done(function(data){
			$("#" + idname).next().next().next().next().next().html(data);

		});
	});


	//権利者ajax追加
//	$('#kenrisya_ajax_add').click(function(){
//		var cnt = Math.floor(Math.random() * 100);
//		var newcid = "customer" + cnt
//		$('.kenrisya_hyouji:first').clone().appendTo('.kenrisya_wrap').
//		find("#customer").attr("id", newcid);
//		$("#" + newcid).next().next("select").attr("id", "addr" + cnt);
//		$("#" + newcid).next().next().next().next("select").attr("id", "daihyo" + cnt);
//
//	});

	$(document).on("click", "#kenrisya_ajax_add", function() {
		var cnt = Math.floor(Math.random() * 100);
		var newcid = "customer" + cnt
		$('.kenrisya_hyouji:first').clone().appendTo('.kenrisya_wrap').
		find("#customer").attr("id", newcid);
		$("#" + newcid).next().next("select").attr("id", "addr" + cnt);
		$("#" + newcid).next().next().next().next("select").attr("id", "daihyo" + cnt);

		$('[id^="customer"]').select2({
            placeholder: "選択してください",
            allowClear: true
        });

    });

	//権利者削除
	$('#kenrisya_ajax_remove').click(function(){
		if($('.kenrisya_hyouji').length != 1){
			$('.kenrisya_hyouji:last-child').remove();

		}
	});
});

