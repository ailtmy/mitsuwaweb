package com.example.demo.soft.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.soft.ShinseiXmlHelper;
import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.ShinseiBukken;
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

	public String xmlFileGet(String shinseiName, Hozon hozon) throws ParserConfigurationException, SAXException, IOException {
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
		hozonElement.getAttributeNode("申請事項").getLastChild().appendChild(syoyusyaElement);
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

		//物件セット
		Element shinseiBukkenJyohoElement = docList.get(0).createElement("申請物件情報");
		hozonElement.getAttributeNode("申請書情報").getLastChild().appendChild(shinseiBukkenJyohoElement);
		for(ShinseiBukken bukken : hozon.getShinseiBukkenList()) {
			if(bukken.getBukkenKubun() == "一棟") {
				Element shinseiBukkenElement = docList.get(0).createElement("申請物件");

				Element bukkenTokuteiJyohoElement = docList.get(0).createElement("物件特定情報");
				shinseiBukkenElement.appendChild(bukkenTokuteiJyohoElement);

				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件種別", bukken.getBukkenSyubetsu());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件指定", "所在");
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番区域情報", bukken.getChibanKuikiJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "地番家屋番号情報", bukken.getChibanKaokubangoJyoho());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "不動産番号", bukken.getFudosanBango());
				ShinseiXmlHelper.elementCreateSet(docList.get(0), bukkenTokuteiJyohoElement, "物件区分", bukken.getBukkenKubun());
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

				ShinseiXmlHelper.elementCreateSet(docList.get(0), ittouNoSyozaiElement, "地番区域", bukken.getChibanKuikiJyoho());


			}
		}

		return "２項保存外部ファイルを作成しました。";
	}


}
