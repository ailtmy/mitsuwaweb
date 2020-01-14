package com.example.demo.soft.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.soft.ShinseiXmlHelper;
import com.example.demo.soft.ZipCreate;
import com.example.demo.soft.entity.FuzokuTatemono;
import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.entity.IttouTatemono;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.SenyuTatemono;
import com.example.demo.soft.entity.Shikichiken;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Tatemono;
import com.example.demo.soft.entity.Tochi;
import com.example.demo.soft.repository.HozonRepository;

@Service
public class HozonService {

	@Autowired
	HozonRepository repository;

	public Page<Hozon> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Hozon find(Integer id) {
		return repository.findById(id).orElse(new Hozon());
	}

	public Hozon saveHozon(Hozon hozon) {
		return repository.saveAndFlush(hozon);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Hozon hozon) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element hozonElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element hozonInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element hozonExportElement = ShinseiXmlHelper.exportElement(docList);

		ShinseiXmlHelper.elementTextset(hozonElement, "原因", hozon.getGenin(),0);
		//所有者セット
		Element syoyusyaElement = docList.get(0).createElement("所有者");
//		hozonElement.getAttributeNode("申請事項").appendChild(syoyusyaElement);
		for(Kenrisya kenrisya : hozon.getSyoyusya()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			syoyusyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent("住所");
			meigininElement.appendChild(addrElement);

			Element mochiElement = docList.get(0).createElement("持");
			mochiElement.setTextContent(kenrisya.getMochibun());
			meigininElement.appendChild(mochiElement);

			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(kenrisya.getCustomer().getName());
			meigininElement.appendChild(nameElement);

			Element daitoriElement = docList.get(0).createElement("代");
			daitoriElement.setTextContent("代表取締役");
			meigininElement.appendChild(daitoriElement);
		}

		for(String syorui : hozon.getTempsyorui().getSyoruis()) {
			ShinseiXmlHelper.elementTextset(hozonElement, "添付書類", syorui, 0);
		}

		ShinseiXmlHelper.elementTextset(hozonElement, "申請年月日", hozon.getDate() + "法第７４条第２項申請",0);
		ShinseiXmlHelper.elementTextset(hozonElement, "宛先登記所名", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "提出先名称", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登記所コード", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "提出先コード", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "課税価格合計額", String.valueOf(hozon.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "課税価格の内訳等", hozon.getKazeiUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税合計額", String.valueOf(hozon.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税内訳等", hozon.getToumenUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税適用条項", hozon.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(hozonInfoElement, "Kemmei", hozon.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "ShinseisakiTokishoCode", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "ShinseisakiTokishoMeisho", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(hozonExportElement, "ファイル名", hozon.getKenmei() + ".zip", 0);

		//物件セット
//		Element shinseiBukkenJyohoElement = docList.get(0).createElement("申請物件情報");
//		hozonElement.getAttributeNode("申請書情報").appendChild(shinseiBukkenJyohoElement);
		for(ShinseiBukken bukken : hozon.getShinseiBukkenList()) {
			if(bukken instanceof IttouTatemono) {
				IttouTatemono ittoubukken = (IttouTatemono) bukken;
				Element shinseiBukkenElement = docList.get(0).createElement("申請物件");

				Element bukkenTokuteiJyohoElement = docList.get(0).createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件種別", ittoubukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番区域情報", ittoubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番家屋番号情報", ittoubukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "不動産番号", ittoubukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件区分", ittoubukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element ittouElement = docList.get(0).createElement("一棟の建物の表示");
				shinseiBukkenElement.appendChild(ittouElement);

				Element kankiElement = docList.get(0).createElement("冠記");
				ittouElement.appendChild(kankiElement);

				Element ittouNoSyozairanElement = docList.get(0).createElement("一棟の所在欄");
				ittouElement.appendChild(ittouNoSyozairanElement);

				Element ittouNoSyozaiElement = docList.get(0).createElement("一棟の所在");
				ittouNoSyozairanElement.appendChild(ittouNoSyozaiElement);

				Element ittouNoTatemonobangoranElement = docList.get(0).createElement("一棟の建物番号欄");
				ittouElement.appendChild(ittouNoTatemonobangoranElement);

				Element ittouNoHyoujirirekiranElement = docList.get(0).createElement("一棟の表示履歴欄");
				ittouElement.appendChild(ittouNoHyoujirirekiranElement);
				Element ittouNoBikoElement = docList.get(0).createElement("備考");
				ittouElement.appendChild(ittouNoBikoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "地番区域", ittoubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "地番区域市区町村", ittoubukken.getShikucyoson());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "地番区域大字", ittoubukken.getOoaza());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "敷地番", ittoubukken.getShikichiban());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "換地等の記載", ittoubukken.getKanchi());

				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoTatemonobangoranElement, "建物番号", ittoubukken.getTatemonoBango());

				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoHyoujirirekiranElement, "構造", ittoubukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoHyoujirirekiranElement, "床面積", ittoubukken.getYukamenseki());
			}

			if(bukken instanceof SenyuTatemono) {
				SenyuTatemono senyubukken = (SenyuTatemono) bukken;
				Element shinseiBukkenElement = docList.get(0).createElement("申請物件");

				Element bukkenTokuteiJyohoElement = docList.get(0).createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件種別", senyubukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番区域情報", senyubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番家屋番号情報", senyubukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "不動産番号", senyubukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件区分", senyubukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element senyuElement = docList.get(0).createElement("専有部分の建物の表示");
				shinseiBukkenElement.appendChild(senyuElement);

				Element kankiElement = docList.get(0).createElement("冠記");
				senyuElement.appendChild(kankiElement);

				Element senyuKaokuBangoranElement = docList.get(0).createElement("専有部分の家屋番号欄");
				senyuElement.appendChild(senyuKaokuBangoranElement);

				Element senyuKaokuBangoElement = docList.get(0).createElement("専有部分の家屋番号");
				senyuKaokuBangoranElement.appendChild(senyuKaokuBangoElement);

				Element senyuNoTatemonobangoranElement = docList.get(0).createElement("専有部分の建物番号欄");
				senyuElement.appendChild(senyuNoTatemonobangoranElement);

				Element senyuNoHyoujirirekiranElement = docList.get(0).createElement("専有部分の表示履歴欄");
				senyuElement.appendChild(senyuNoHyoujirirekiranElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuKaokuBangoElement, "地番区域", senyubukken.getOoaza());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuKaokuBangoElement, "家屋番号", senyubukken.getKaokuBango());

				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuNoTatemonobangoranElement, "建物の番号", senyubukken.getTatemonoBango());

				Element senyuNokankiElement = docList.get(0).createElement("冠記");
				senyuNoHyoujirirekiranElement.appendChild(senyuNokankiElement);
				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuNoHyoujirirekiranElement, "種類", senyubukken.getSyurui());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuNoHyoujirirekiranElement, "構造", senyubukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuNoHyoujirirekiranElement, "床面積", senyubukken.getYukamenseki());
				Element taisyoTokiElement = docList.get(0).createElement("対象登記");
				senyuNoHyoujirirekiranElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = docList.get(0).createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = docList.get(0).createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = docList.get(0).createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);
				ShinseiXmlHelper.elementCreateSet(docList.get(0), senyuNoHyoujirirekiranElement, "備考", senyubukken.getBiko());

				for(Shikichiken shikichi : senyubukken.getShikichiken()) {
					Element shikichikennohyojiElement = docList.get(0).createElement("敷地権の表示欄");
					senyuElement.appendChild(shikichikennohyojiElement);

					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "土地の符号", shikichi.getShikichifugo());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "所在及び地番", shikichi.getShikichisyozaichiban());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "地目", shikichi.getShikichichimoku());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "地積", shikichi.getShikichichiseki());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "敷地権の種類", shikichi.getShikichisyurui());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "敷地権の割合", shikichi.getShikichiwariai());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), shikichikennohyojiElement, "備考", shikichi.getShikichibiko());
				}

				for(FuzokuTatemono fuzoku : senyubukken.getFuzokuTatemono()) {
					Element fuzokuTatemononohyoujiElement = docList.get(0).createElement("附属建物の表示欄");
					senyuElement.appendChild(fuzokuTatemononohyoujiElement);

					Element fuzokuNokankiElement = docList.get(0).createElement("冠記");
					fuzokuTatemononohyoujiElement.appendChild(fuzokuNokankiElement);

					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "符号", fuzoku.getFuzokufugo());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "種類", fuzoku.getFuzokusyurui());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "構造", fuzoku.getFuzokukozo());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "床面積", fuzoku.getFuzokuyukamenseki());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "備考", fuzoku.getFuzokubiko());
				}
			}

			if(bukken instanceof Tochi) {
				Tochi tochibukken = (Tochi) bukken;
				Element shinseiBukkenElement = docList.get(0).createElement("申請物件");

				Element bukkenTokuteiJyohoElement = docList.get(0).createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件種別", tochibukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番区域情報", tochibukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番家屋番号情報", tochibukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "不動産番号", tochibukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件区分", tochibukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element tochiElement = docList.get(0).createElement("土地の表示");
				shinseiBukkenElement.appendChild(tochiElement);

				Element kankiElement = docList.get(0).createElement("冠記");
				tochiElement.appendChild(kankiElement);

				Element tochisyozairanElement = docList.get(0).createElement("土地の所在欄");
				tochiElement.appendChild(tochisyozairanElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tochisyozairanElement, "土地の所在", tochibukken.getSyozai());

				Element tochiNoHyoujirirekiranElement = docList.get(0).createElement("土地の表示履歴欄");
				tochiElement.appendChild(tochiNoHyoujirirekiranElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tochiNoHyoujirirekiranElement, "地番", tochibukken.getChiban());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tochiNoHyoujirirekiranElement, "地目", tochibukken.getChimoku());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tochiNoHyoujirirekiranElement, "地積", tochibukken.getChiseki());

				Element taisyoTokiElement = docList.get(0).createElement("対象登記");
				tochiElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = docList.get(0).createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = docList.get(0).createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = docList.get(0).createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tochiElement, "備考", tochibukken.getBiko());
			}

			if(bukken instanceof Tatemono) {
				Tatemono Tatemonobukken = (Tatemono) bukken;
				Element shinseiBukkenElement = docList.get(0).createElement("申請物件");

				Element bukkenTokuteiJyohoElement = docList.get(0).createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件種別", Tatemonobukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番区域情報", Tatemonobukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番家屋番号情報", Tatemonobukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "不動産番号", Tatemonobukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件区分", Tatemonobukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element tatemonoElement = docList.get(0).createElement("建物の表示");
				shinseiBukkenElement.appendChild(tatemonoElement);

				Element kankiElement = docList.get(0).createElement("冠記");
				tatemonoElement.appendChild(kankiElement);

				Element tatemonosyozairanElement = docList.get(0).createElement("建物の所在欄");
				tatemonoElement.appendChild(tatemonosyozairanElement);

				Element tatemonoNosyozainElement = docList.get(0).createElement("建物の所在");
				tatemonosyozairanElement.appendChild(tatemonoNosyozainElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNosyozainElement, "地番区域", Tatemonobukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNosyozainElement, "敷地番", Tatemonobukken.getShikichiban());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNosyozainElement, "換地等の記載", Tatemonobukken.getKanchi());

				Element tatemonoKaokubangoranElement = docList.get(0).createElement("建物の家屋番号欄");
				tatemonoElement.appendChild(tatemonoKaokubangoranElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoKaokubangoranElement, "家屋番号", Tatemonobukken.getKaokuBango());

				Element tatemonoNoHyoujirirekiranElement = docList.get(0).createElement("建物の表示履歴欄");
				tatemonoElement.appendChild(tatemonoNoHyoujirirekiranElement);

				Element hyoujirirekikankiElement = docList.get(0).createElement("冠記");
				tatemonoNoHyoujirirekiranElement.appendChild(hyoujirirekikankiElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNoHyoujirirekiranElement, "種類", Tatemonobukken.getSyurui());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNoHyoujirirekiranElement, "構造", Tatemonobukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNoHyoujirirekiranElement, "床面積", Tatemonobukken.getYukamenseki());

				Element taisyoTokiElement = docList.get(0).createElement("対象登記");
				tatemonoNoHyoujirirekiranElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = docList.get(0).createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = docList.get(0).createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = docList.get(0).createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), tatemonoNoHyoujirirekiranElement, "備考", Tatemonobukken.getBiko());

				for(FuzokuTatemono fuzoku : Tatemonobukken.getFuzokuTatemono()) {
					Element fuzokuTatemononohyoujiElement = docList.get(0).createElement("附属建物の表示欄");
					tatemonoElement.appendChild(fuzokuTatemononohyoujiElement);

					Element fuzokuNokankiElement = docList.get(0).createElement("冠記");
					fuzokuTatemononohyoujiElement.appendChild(fuzokuNokankiElement);

					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "符号", fuzoku.getFuzokufugo());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "種類", fuzoku.getFuzokusyurui());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "構造", fuzoku.getFuzokukozo());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "床面積", fuzoku.getFuzokuyukamenseki());
					ShinseiXmlHelper.elementCreateSet(docList.get(0), fuzokuTatemononohyoujiElement, "備考", fuzoku.getFuzokubiko());
				}
			}

			ShinseiXmlHelper.makeFolder(shinseiName, hozon.getKenmei());

			ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, hozon.getKenmei());


	        // ZIP化実施フォルダを取得
	     	File destination = new File("./" + hozon.getKenmei() + "s/" + hozon.getKenmei() + ".zip");
	     	File dirzip = new File("./" + hozon.getKenmei());

	     	// 圧縮実行
	     	ZipCreate.compressDirectory(destination, dirzip);

	     	// ZIP化実施フォルダを取得
	     	File dir = new File("./" + hozon.getKenmei() + "s");

	     	// ZIP化実施フォルダを取得
	     	File destination2 = new File("./" + hozon.getKenmei() + ".zip");

	     	// 圧縮実行
	     	ZipCreate.compressDirectory(destination2, dir);
		}

		return "２項保存外部ファイルを作成しました。";
	}


}
