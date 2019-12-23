$(document).ready(function(){

	//未失効物件追加
	$('#mishikko_bukken').click(function(){
		$('.mishikko_bukken_hyouji:last').clone().appendTo('.mishikko_bukken_wrap');
	});

	//未失効物件削除
	$('#mishikko_bukken_remove').click(function(){
		$('.mishikko_bukken_hyouji:last-child').remove();
	});

	//一棟の建物物件追加
	$('#ittou_tatemono_hyouji').click(function(){
		var html = '<div class="ittou_tatemono">' +
						'<div class="form-group">' +
							'<label for="chibanKaokubango">一棟地番家屋番号情報</label>' +
							'<input type="text" name="chibanKaokubango" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="shikucyoson">地番区域市区町村</label>' +
							'<input type="text" name="shikucyoson" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="ooaza">地番区域大字</label>' +
							'<input type="text" name="ooaza" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="shikichiban">敷地番</label>' +
							'<input type="text" name="shikichiban" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="kanchi">換地等の記載</label>' +
							'<input type="text" name="kanchi" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="tatemonoBango">建物番号</label>' +
							'<input type="text" name="tatemonoBango" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="kozo">構造</label>' +
							'<input type="text" name="kozo" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="yukamenseki">床面積</label>' +
							'<input type="text" name="yukamenseki" class="form-control"/>' +
						'</div>' +
							'<div class="form-group">' +
							'<label for="biko">備考</label>' +
							'<input type="text" name="biko" class="form-control"/>' +
						'</div>' +
					'</div>';
		$('.shinseibukken_hyouji').append(html);
	});

	//一棟の建物削除
	$('#ittou_tatemono_remove').click(function(){
		$('.ittou_tatemono:last-child').remove();
	});

	//専有部分建物追加
	$('#senyu_tatemono_hyouji').click(function(){
		var html = '<div class="senyu_tatemono">' +
						'<div class="form-group">' +
							'<label for="chibanKaokubango">一棟地番家屋番号情報</label>' +
							'<input type="text" name="chibanKaokubango" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="shikucyoson">地番区域市区町村</label>' +
							'<input type="text" name="shikucyoson" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="ooaza">地番区域大字</label>' +
							'<input type="text" name="ooaza" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="shikichiban">敷地番</label>' +
							'<input type="text" name="shikichiban" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="kanchi">換地等の記載</label>' +
							'<input type="text" name="kanchi" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="tatemonoBango">建物番号</label>' +
							'<input type="text" name="tatemonoBango" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="kozo">構造</label>' +
							'<input type="text" name="kozo" class="form-control"/>' +
						'</div>' +
						'<div class="form-group">' +
							'<label for="yukamenseki">床面積</label>' +
							'<input type="text" name="yukamenseki" class="form-control"/>' +
						'</div>' +
							'<div class="form-group">' +
							'<label for="biko">備考</label>' +
							'<input type="text" name="biko" class="form-control"/>' +
						'</div>' +
					'</div>';
		$('.shinseibukken_hyouji').append(html);
	});

});