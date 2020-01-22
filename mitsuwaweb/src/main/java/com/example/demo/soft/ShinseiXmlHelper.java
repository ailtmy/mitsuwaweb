package com.example.demo.soft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serializer.OutputPropertiesFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.demo.soft.entity.FuzokuTatemono;
import com.example.demo.soft.entity.IttouTatemono;
import com.example.demo.soft.entity.SenyuTatemono;
import com.example.demo.soft.entity.Shikichiken;
import com.example.demo.soft.entity.ShinseiBukken;
import com.example.demo.soft.entity.Shinseisyo;
import com.example.demo.soft.entity.Tatemono;
import com.example.demo.soft.entity.Tochi;

public class ShinseiXmlHelper {

	/**
	 * DocumentList作成
	 * @param builder DocumentBuilderFactory.newInstance().newDocumentBuilder
	 * @param strList fileParh
	 * @return List<Document>
	 * @throws SAXException
	 * @throws IOException
	 */
	public static List<Document> getDocument(DocumentBuilder builder, String str) throws SAXException, IOException {

		List<Document> docList = new ArrayList<Document>();
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/" + str + ".xml").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/" + str + ".xsl").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/syomei/index.rdf").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/info.xml").toFile()));
		docList.add(builder.parse(Paths.get("./src/main/resources/hinagata/" + str + "/export.xml").toFile()));
		return docList;
	}

	/**
	 * docListのindex0は申請書のxmlファイル想定
	 * @param docList
	 * @return
	 */
	public static Element xmlElement(List<Document> docList) {
		return docList.get(0).getDocumentElement();
	}

	public static Element infoElement(List<Document> docList) {
		return docList.get(3).getDocumentElement();
	}

	public static Element exportElement(List<Document> docList) {
		return docList.get(4).getDocumentElement();
	}

	/**
	 * 指定タグにtextContentをセット
	 * @param element
	 * @param tagName
	 * @param contentSet
	 */
	public static void elementTextset(Element element, String tagName, String contentSet, int count) {

		element.getElementsByTagName(tagName).item(count).setTextContent(contentSet);
	}

	/**
	 * タグ作成
	 * @param document
	 * @param parentElement
	 * @param childTagName
	 * @param textContent
	 */
	public static void elementCreateSet(
			 Document document, Element parentElement, String childTagName, String textContent) {
		Element childElement = document.createElement(childTagName);
		if(textContent == null || textContent.isEmpty()) {
			childElement.setTextContent("");
		}else {
			childElement.setTextContent(textContent);
		}
		parentElement.appendChild(childElement);
	}

	/**
	 * 指定タグ内の子要素を含めてクローン作成
	 * @param element
	 * @param findTagName 指定タグ
	 * @param addTagName このタグの直前に要素を追加
	 */
	public static void elementAdd(Element element, String findTagName, String addTagName) {

		element.getElementsByTagName(addTagName).item(0).appendChild(
				element.getElementsByTagName(findTagName).item(0).cloneNode(true));
	}

	/**
	 * 申請用フォルダ作成
	 * @param fileName 手続ファイル名(ex.HM0504000300001)
	 * @throws IOException
	 */
	public static void makeFolder(String fileName, String folderName) throws IOException {

		File togo = new File("./" + folderName + "s/");
		File export = new File("./" + folderName + "/");
		File kanri = new File("./" + folderName + "/署名・送信/");
		File oshirase = new File("./" + folderName + "/お知らせ/");
		File kobunsyo = new File("./" + folderName + "/取得公文書/");
		File htmlFile = new File("./" + folderName + "/" + fileName + ".html");
		File oshiraseDummy = new File("./" + folderName + "/お知らせ/dummy.txt");
		File kobunsyoDummy = new File("./" + folderName + "/取得公文書/dummy.txt");

		togo.mkdir();
		export.mkdir();
		kanri.mkdir();
		oshirase.mkdir();
		kobunsyo.mkdir();
		htmlFile.createNewFile();
		oshiraseDummy.createNewFile();
		kobunsyoDummy.createNewFile();

	}

	/**
	 * 申請物件作成
	 * @param doc
	 * @param fileName
	 * @param folderName
	 * @throws TransformerException
	 */
	public static void makeShinseiFile(List<Document> doc, String fileName, String folderName) throws TransformerException {

		TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tf = tfFactory.newTransformer();

        tf.setOutputProperty("indent", "yes");
        tf.setOutputProperty("encoding", "UTF-8");
        tf.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");


        tf.transform(new DOMSource(doc.get(0)), new StreamResult(new File("./" + folderName + "/署名・送信/" + fileName + ".xml")));
        tf.transform(new DOMSource(doc.get(1)), new StreamResult(new File("./" + folderName + "/署名・送信/" + fileName + ".xsl")));
        tf.transform(new DOMSource(doc.get(2)), new StreamResult(new File("./" + folderName + "/署名・送信/index.rdf")));
        tf.transform(new DOMSource(doc.get(3)), new StreamResult(new File("./" + folderName + "/info.xml")));
        tf.transform(new DOMSource(doc.get(4)), new StreamResult(new File("./" + folderName + "s/export.xml")));

	}

	public static void makeShinseiBukken(Document document, Shinseisyo shinseisyo) {
		Element shinseiBukkenJyohoElement = document.createElement("申請物件情報");
		NodeList shinseisyojyoho = document.getElementsByTagName("申請書情報");
		shinseisyojyoho.item(0).appendChild(shinseiBukkenJyohoElement);
		for(ShinseiBukken bukken : shinseisyo.getShinseiBukkenList()) {
			if(bukken instanceof IttouTatemono) {
				IttouTatemono ittoubukken = (IttouTatemono) bukken;
				Element shinseiBukkenElement = document.createElement("申請物件");
				shinseiBukkenJyohoElement.appendChild(shinseiBukkenElement);
				Element bukkenTokuteiJyohoElement = document.createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件種別", ittoubukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番区域情報", ittoubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番家屋番号情報", ittoubukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "不動産番号", ittoubukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件区分", ittoubukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element ittouElement = document.createElement("一棟の建物の表示");
				shinseiBukkenElement.appendChild(ittouElement);

				Element kankiElement = document.createElement("冠記");
				ittouElement.appendChild(kankiElement);

				Element ittouNoSyozairanElement = document.createElement("一棟の所在欄");
				ittouElement.appendChild(ittouNoSyozairanElement);

				Element ittouNoSyozaiElement = document.createElement("一棟の所在");
				ittouNoSyozairanElement.appendChild(ittouNoSyozaiElement);

				Element ittouNoTatemonobangoranElement = document.createElement("一棟の建物番号欄");
				ittouElement.appendChild(ittouNoTatemonobangoranElement);

				Element ittouNoHyoujirirekiranElement = document.createElement("一棟の表示履歴欄");
				ittouElement.appendChild(ittouNoHyoujirirekiranElement);
				Element ittouNoBikoElement = document.createElement("備考");
				ittouElement.appendChild(ittouNoBikoElement);

				ShinseiXmlHelper.elementCreateSet(document, ittouNoSyozaiElement, "地番区域", ittoubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, ittouNoSyozaiElement, "地番区域市区町村", ittoubukken.getShikucyoson());
				ShinseiXmlHelper.elementCreateSet(document, ittouNoSyozaiElement, "地番区域大字", ittoubukken.getOoaza());
				ShinseiXmlHelper.elementCreateSet(document, ittouNoSyozaiElement, "敷地番", ittoubukken.getShikichiban());
				ShinseiXmlHelper.elementCreateSet(document, ittouNoSyozaiElement, "換地等の記載", ittoubukken.getKanchi());

				ShinseiXmlHelper.elementCreateSet(document, ittouNoTatemonobangoranElement, "建物番号", ittoubukken.getTatemonoBango());

				ShinseiXmlHelper.elementCreateSet(document, ittouNoHyoujirirekiranElement, "構造", ittoubukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(document, ittouNoHyoujirirekiranElement, "床面積", ittoubukken.getYukamenseki());
			}

			if(bukken instanceof SenyuTatemono) {
				SenyuTatemono senyubukken = (SenyuTatemono) bukken;
				Element shinseiBukkenElement = document.createElement("申請物件");
				shinseiBukkenJyohoElement.appendChild(shinseiBukkenElement);
				Element bukkenTokuteiJyohoElement = document.createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件種別", senyubukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番区域情報", senyubukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番家屋番号情報", senyubukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "不動産番号", senyubukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件区分", senyubukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element senyuElement = document.createElement("専有部分の建物の表示");
				shinseiBukkenElement.appendChild(senyuElement);

				Element kankiElement = document.createElement("冠記");
				senyuElement.appendChild(kankiElement);

				Element senyuKaokuBangoranElement = document.createElement("専有部分の家屋番号欄");
				senyuElement.appendChild(senyuKaokuBangoranElement);

				Element senyuKaokuBangoElement = document.createElement("専有部分の家屋番号");
				senyuKaokuBangoranElement.appendChild(senyuKaokuBangoElement);

				Element senyuNoTatemonobangoranElement = document.createElement("専有部分の建物番号欄");
				senyuElement.appendChild(senyuNoTatemonobangoranElement);

				Element senyuNoHyoujirirekiranElement = document.createElement("専有部分の表示履歴欄");
				senyuElement.appendChild(senyuNoHyoujirirekiranElement);

				ShinseiXmlHelper.elementCreateSet(document, senyuKaokuBangoElement, "地番区域", senyubukken.getOoaza());
				ShinseiXmlHelper.elementCreateSet(document, senyuKaokuBangoElement, "家屋番号", senyubukken.getKaokuBango());

				ShinseiXmlHelper.elementCreateSet(document, senyuNoTatemonobangoranElement, "建物の番号", senyubukken.getTatemonoBango());

				Element senyuNokankiElement = document.createElement("冠記");
				senyuNoHyoujirirekiranElement.appendChild(senyuNokankiElement);
				ShinseiXmlHelper.elementCreateSet(document, senyuNoHyoujirirekiranElement, "種類", senyubukken.getSyurui());
				ShinseiXmlHelper.elementCreateSet(document, senyuNoHyoujirirekiranElement, "構造", senyubukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(document, senyuNoHyoujirirekiranElement, "床面積", senyubukken.getYukamenseki());
				Element taisyoTokiElement = document.createElement("対象登記");
				senyuNoHyoujirirekiranElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = document.createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = document.createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = document.createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);
				ShinseiXmlHelper.elementCreateSet(document, senyuNoHyoujirirekiranElement, "備考", senyubukken.getBiko());

				for(Shikichiken shikichi : senyubukken.getShikichiken()) {
					Element shikichikennohyojiElement = document.createElement("敷地権の表示欄");
					senyuElement.appendChild(shikichikennohyojiElement);

					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "土地の符号", shikichi.getShikichifugo());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "所在及び地番", shikichi.getShikichisyozaichiban());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "地目", shikichi.getShikichichimoku());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "地積", shikichi.getShikichichiseki());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "敷地権の種類", shikichi.getShikichisyurui());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "敷地権の割合", shikichi.getShikichiwariai());
					ShinseiXmlHelper.elementCreateSet(document, shikichikennohyojiElement, "備考", shikichi.getShikichibiko());
				}

				for(FuzokuTatemono fuzoku : senyubukken.getFuzokuTatemono()) {
					Element fuzokuTatemononohyoujiElement = document.createElement("附属建物の表示欄");
					senyuElement.appendChild(fuzokuTatemononohyoujiElement);

					Element fuzokuNokankiElement = document.createElement("冠記");
					fuzokuTatemononohyoujiElement.appendChild(fuzokuNokankiElement);

					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "符号", fuzoku.getFuzokufugo());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "種類", fuzoku.getFuzokusyurui());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "構造", fuzoku.getFuzokukozo());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "床面積", fuzoku.getFuzokuyukamenseki());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "備考", fuzoku.getFuzokubiko());
				}
			}

			if(bukken instanceof Tochi) {
				Tochi tochibukken = (Tochi) bukken;
				Element shinseiBukkenElement = document.createElement("申請物件");
				shinseiBukkenJyohoElement.appendChild(shinseiBukkenElement);
				Element bukkenTokuteiJyohoElement = document.createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件種別", tochibukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番区域情報", tochibukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番家屋番号情報", tochibukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "不動産番号", tochibukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件区分", tochibukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element tochiElement = document.createElement("土地の表示");
				shinseiBukkenElement.appendChild(tochiElement);

				Element kankiElement = document.createElement("冠記");
				tochiElement.appendChild(kankiElement);

				Element tochisyozairanElement = document.createElement("土地の所在欄");
				tochiElement.appendChild(tochisyozairanElement);

				ShinseiXmlHelper.elementCreateSet(document, tochisyozairanElement, "土地の所在", tochibukken.getSyozai());

				Element tochiNoHyoujirirekiranElement = document.createElement("土地の表示履歴欄");
				tochiElement.appendChild(tochiNoHyoujirirekiranElement);

				ShinseiXmlHelper.elementCreateSet(document, tochiNoHyoujirirekiranElement, "地番", tochibukken.getChiban());
				ShinseiXmlHelper.elementCreateSet(document, tochiNoHyoujirirekiranElement, "地目", tochibukken.getChimoku());
				ShinseiXmlHelper.elementCreateSet(document, tochiNoHyoujirirekiranElement, "地積", tochibukken.getChiseki());

				Element taisyoTokiElement = document.createElement("対象登記");
				tochiElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = document.createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = document.createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = document.createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);

				ShinseiXmlHelper.elementCreateSet(document, tochiElement, "備考", tochibukken.getBiko());
			}

			if(bukken instanceof Tatemono) {
				Tatemono Tatemonobukken = (Tatemono) bukken;
				Element shinseiBukkenElement = document.createElement("申請物件");
				shinseiBukkenJyohoElement.appendChild(shinseiBukkenElement);
				Element bukkenTokuteiJyohoElement = document.createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件種別", Tatemonobukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番区域情報", Tatemonobukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "地番家屋番号情報", Tatemonobukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "不動産番号", Tatemonobukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件区分", Tatemonobukken.getBukkenKubun());
				ShinseiXmlHelper.elementCreateSet(document, bukkenTokuteiJyohoElement, "物件状態", "既存");

				Element tatemonoElement = document.createElement("建物の表示");
				shinseiBukkenElement.appendChild(tatemonoElement);

				Element kankiElement = document.createElement("冠記");
				tatemonoElement.appendChild(kankiElement);

				Element tatemonosyozairanElement = document.createElement("建物の所在欄");
				tatemonoElement.appendChild(tatemonosyozairanElement);

				Element tatemonoNosyozainElement = document.createElement("建物の所在");
				tatemonosyozairanElement.appendChild(tatemonoNosyozainElement);

				ShinseiXmlHelper.elementCreateSet(document, tatemonoNosyozainElement, "地番区域", Tatemonobukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(document, tatemonoNosyozainElement, "敷地番", Tatemonobukken.getShikichiban());
				ShinseiXmlHelper.elementCreateSet(document, tatemonoNosyozainElement, "換地等の記載", Tatemonobukken.getKanchi());

				Element tatemonoKaokubangoranElement = document.createElement("建物の家屋番号欄");
				tatemonoElement.appendChild(tatemonoKaokubangoranElement);

				ShinseiXmlHelper.elementCreateSet(document, tatemonoKaokubangoranElement, "家屋番号", Tatemonobukken.getKaokuBango());

				Element tatemonoNoHyoujirirekiranElement = document.createElement("建物の表示履歴欄");
				tatemonoElement.appendChild(tatemonoNoHyoujirirekiranElement);

				Element hyoujirirekikankiElement = document.createElement("冠記");
				tatemonoNoHyoujirirekiranElement.appendChild(hyoujirirekikankiElement);

				ShinseiXmlHelper.elementCreateSet(document, tatemonoNoHyoujirirekiranElement, "種類", Tatemonobukken.getSyurui());
				ShinseiXmlHelper.elementCreateSet(document, tatemonoNoHyoujirirekiranElement, "構造", Tatemonobukken.getKozo());
				ShinseiXmlHelper.elementCreateSet(document, tatemonoNoHyoujirirekiranElement, "床面積", Tatemonobukken.getYukamenseki());

				Element taisyoTokiElement = document.createElement("対象登記");
				tatemonoNoHyoujirirekiranElement.appendChild(taisyoTokiElement);
				Element taisyoTokiNoJyuniBangoElement = document.createElement("対象登記の順位番号");
				taisyoTokiElement.appendChild(taisyoTokiNoJyuniBangoElement);
				Element syuTokiNoJyuniBangoElement = document.createElement("主登記の順位番号");
				taisyoTokiNoJyuniBangoElement.appendChild(syuTokiNoJyuniBangoElement);
				Element dojyuniFukiElement = document.createElement("同順位付記情報");
				taisyoTokiNoJyuniBangoElement.appendChild(dojyuniFukiElement);

				ShinseiXmlHelper.elementCreateSet(document, tatemonoNoHyoujirirekiranElement, "備考", Tatemonobukken.getBiko());

				for(FuzokuTatemono fuzoku : Tatemonobukken.getFuzokuTatemono()) {
					Element fuzokuTatemononohyoujiElement = document.createElement("附属建物の表示欄");
					tatemonoElement.appendChild(fuzokuTatemononohyoujiElement);

					Element fuzokuNokankiElement = document.createElement("冠記");
					fuzokuTatemononohyoujiElement.appendChild(fuzokuNokankiElement);

					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "符号", fuzoku.getFuzokufugo());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "種類", fuzoku.getFuzokusyurui());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "構造", fuzoku.getFuzokukozo());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "床面積", fuzoku.getFuzokuyukamenseki());
					ShinseiXmlHelper.elementCreateSet(document, fuzokuTatemononohyoujiElement, "備考", fuzoku.getFuzokubiko());
				}
			}
		}
	}
}
