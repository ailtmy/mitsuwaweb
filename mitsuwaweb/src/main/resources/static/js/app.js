$(document).ready(function(){

	//未失効物件追加
	$('#mishikko_bukken').click(function(){
		$('.mishikko_bukken_hyouji:last').clone().appendTo('.mishikko_bukken_wrap');
	});

	//未失効物件削除
	$('#mishikko_bukken_remove').click(function(){
		$('.mishikko_bukken_hyouji:last-child').remove();
	});
});