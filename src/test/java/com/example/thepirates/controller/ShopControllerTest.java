package com.example.thepirates.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.thepirates.common.converter.DayOfWeekConverter;
import com.example.thepirates.controller.request.ShopRequest;
import com.example.thepirates.error.exception.shop.OpenTimeEqualsCloseTimeException;
import com.example.thepirates.error.exception.shop.ShopNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("점포 관련 테스트")
public class ShopControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	MockMvc mockMvc;

	String url = "/api/shops";

	@Autowired
	ObjectMapper objectMapper;

	private DayOfWeekConverter dayOfWeekConverter;

	@BeforeEach
	public void setUp() throws Exception {
		dayOfWeekConverter = new DayOfWeekConverter();

		mockMvc = MockMvcBuilders
				.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();

		List<ShopRequest.ShopCreate.BusinessTime> mermaidBusinessTimes = new ArrayList<>();
		ShopRequest.ShopCreate.BusinessTime mermaidBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		mermaidBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		mermaidBusinessTime.setOpen(LocalTime.of(13, 0));
		mermaidBusinessTime.setClose(LocalTime.of(23, 0));
		mermaidBusinessTimes.add(mermaidBusinessTime);

		mermaidBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		mermaidBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Tuesday"));
		mermaidBusinessTime.setOpen(LocalTime.of(13, 0));
		mermaidBusinessTime.setClose(LocalTime.of(23, 0));
		mermaidBusinessTimes.add(mermaidBusinessTime);

		mermaidBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		mermaidBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Wednesday"));
		mermaidBusinessTime.setOpen(LocalTime.of(9, 0));
		mermaidBusinessTime.setClose(LocalTime.of(18, 0));
		mermaidBusinessTimes.add(mermaidBusinessTime);

		mermaidBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		mermaidBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Thursday"));
		mermaidBusinessTime.setOpen(LocalTime.of(9, 0));
		mermaidBusinessTime.setClose(LocalTime.of(23, 0));
		mermaidBusinessTimes.add(mermaidBusinessTime);

		mermaidBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		mermaidBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Friday"));
		mermaidBusinessTime.setOpen(LocalTime.of(9, 0));
		mermaidBusinessTime.setClose(LocalTime.of(23, 0));
		mermaidBusinessTimes.add(mermaidBusinessTime);

		ShopRequest.ShopCreate mermaidShopCreate = ShopRequest.ShopCreate.builder()
				.name("인어수산")
				.owner("장인어")
				.description("인천소래포구 종합어시장 갑각류센터 인어수산")
				.level(2)
				.address("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1 층 1 호")
				.phone("010-1111-2222")
				.businessTimes(mermaidBusinessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(mermaidShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		List<ShopRequest.ShopCreate.BusinessTime> pirateBusinessTimes = new ArrayList<>();
		ShopRequest.ShopCreate.BusinessTime pirateBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		pirateBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		pirateBusinessTime.setOpen(LocalTime.of(9, 0));
		pirateBusinessTime.setClose(LocalTime.of(23, 0));
		pirateBusinessTimes.add(pirateBusinessTime);

		pirateBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		pirateBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Tuesday"));
		pirateBusinessTime.setOpen(LocalTime.of(9, 0));
		pirateBusinessTime.setClose(LocalTime.of(0, 0));
		pirateBusinessTimes.add(pirateBusinessTime);

		pirateBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		pirateBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Wednesday"));
		pirateBusinessTime.setOpen(LocalTime.of(9, 0));
		pirateBusinessTime.setClose(LocalTime.of(0, 0));
		pirateBusinessTimes.add(pirateBusinessTime);

		pirateBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		pirateBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Thursday"));
		pirateBusinessTime.setOpen(LocalTime.of(9, 0));
		pirateBusinessTime.setClose(LocalTime.of(0, 0));
		pirateBusinessTimes.add(pirateBusinessTime);

		pirateBusinessTime = new ShopRequest.ShopCreate.BusinessTime();
		pirateBusinessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Friday"));
		pirateBusinessTime.setOpen(LocalTime.of(9, 0));
		pirateBusinessTime.setClose(LocalTime.of(0, 0));
		pirateBusinessTimes.add(pirateBusinessTime);

		ShopRequest.ShopCreate pirateShopCreate = ShopRequest.ShopCreate.builder()
				.name("해적수산")
				.owner("박해적")
				.description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
				.level(1)
				.address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
				.phone("010-1234-1234")
				.businessTimes(pirateBusinessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(pirateShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

	}

	@Test
	@DisplayName("점포 추가 - 성공")
	public void 점포_추가_성공() throws Exception{
		List<ShopRequest.ShopCreate.BusinessTime> businessTimes = new ArrayList<>();

		ShopRequest.ShopCreate.BusinessTime businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Tuesday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Wednesday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Thursday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Friday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		ShopRequest.ShopCreate shopCreateShopCreate = ShopRequest.ShopCreate.builder()
				.name("테스트수산")
				.owner("테스트이름")
				.description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
				.level(1)
				.address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
				.phone("010-1234-1234")
				.businessTimes(businessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(shopCreateShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

	}

	@Test
	@DisplayName("점포 추가 - 실패 - 상점 미입력")
	public void 점포_추가_실패_상점_미입력() throws Exception{
		List<ShopRequest.ShopCreate.BusinessTime> businessTimes = new ArrayList<>();

		ShopRequest.ShopCreate.BusinessTime businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		ShopRequest.ShopCreate shopCreateShopCreate = ShopRequest.ShopCreate.builder()
				.owner("테스트이름")
				.description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
				.level(1)
				.address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
				.phone("010-1234-1234")
				.businessTimes(businessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(shopCreateShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("점포 추가 - 실패 - 전화번호 포맷 다를때")
	public void 점포_추가_전화번호_포맷_실패() throws Exception{
		List<ShopRequest.ShopCreate.BusinessTime> businessTimes = new ArrayList<>();

		ShopRequest.ShopCreate.BusinessTime businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		businessTime.setOpen(LocalTime.of(9, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		ShopRequest.ShopCreate shopCreateShopCreate = ShopRequest.ShopCreate.builder()
				.name("테스트상점")
				.owner("테스트이름")
				.description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
				.level(1)
				.address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
				.phone("010-1234-12341")
				.businessTimes(businessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(shopCreateShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("점포 추가 - 영업 시작, 종료시간 같을 때 - 실패")
	public void 점포_추가_영업시작_종료시간_같을때_실패() throws Exception{
		List<ShopRequest.ShopCreate.BusinessTime> businessTimes = new ArrayList<>();

		ShopRequest.ShopCreate.BusinessTime businessTime = new ShopRequest.ShopCreate.BusinessTime();
		businessTime.setDay(dayOfWeekConverter.convertToEntityAttribute("Monday"));
		businessTime.setOpen(LocalTime.of(0, 0));
		businessTime.setClose(LocalTime.of(0, 0));
		businessTimes.add(businessTime);

		ShopRequest.ShopCreate shopCreateShopCreate = ShopRequest.ShopCreate.builder()
				.name("테스트상점")
				.owner("테스트이름")
				.description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
				.level(1)
				.address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
				.phone("010-1234-1234")
				.businessTimes(businessTimes)
				.build();

		mockMvc.perform(post(url)
				.content(objectMapper.writeValueAsString(shopCreateShopCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value("4001"))
				.andExpect(jsonPath("$.message").value("오픈 시간과 마감 시간이 같습니다."))
				.andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
						OpenTimeEqualsCloseTimeException.class)))
		;
	}

	@Test
	@DisplayName("점포 휴무일 등록 - 성공")
	public void 점포_휴무일_등록_성공() throws Exception {
		ShopRequest.HolidayCreate holidayCreate = new ShopRequest.HolidayCreate();
		holidayCreate.setId(2L);
		List<LocalDate> holidays = new ArrayList<>();
		holidays.add(LocalDate.of(2021, 5, 22));
		holidays.add(LocalDate.of(2021, 5, 23));
		holidayCreate.setHolidays(holidays);

		mockMvc.perform(post(url+"/holiday")
				.content(objectMapper.writeValueAsString(holidayCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("점포 휴무일 등록 - 실패 - 없는 점포")
	public void 점포_휴무일_등록_실패() throws Exception {
		ShopRequest.HolidayCreate holidayCreate = new ShopRequest.HolidayCreate();
		holidayCreate.setId(-1L);
		List<LocalDate> holidays = new ArrayList<>();
		holidays.add(LocalDate.of(2021, 5, 22));
		holidays.add(LocalDate.of(2021, 5, 23));
		holidayCreate.setHolidays(holidays);

		mockMvc.perform(post(url+"/holiday")
				.content(objectMapper.writeValueAsString(holidayCreate))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
						ShopNotFoundException.class)))
		;
	}

	@Test
	@DisplayName("점포 목록 조회 - 성공")
	public void 점포_목록_조회_성공() throws Exception {
		mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isOk())
		;
	}

	@Test
	@DisplayName("점포 상세 정보 조회 - 성공")
	public void 점포_상세_조회_성공() throws Exception {
		mockMvc.perform(get(url+"/{shopId}", 2 ))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.description").exists())
				.andExpect(jsonPath("$.level").exists())
				.andExpect(jsonPath("$.phone").exists())
				.andExpect(jsonPath("$.businessDays").exists())
		;
	}

	@Test
	@DisplayName("점포 상세 정보 조회 - 실패")
	public void 점포_상세_조회_실패() throws Exception {
		mockMvc.perform(get(url+"/{shopId}", -1 ))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value("4002"))
				.andExpect(jsonPath("$.message").value("점포가 존재하지 않습니다."))
				.andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getClass().isAssignableFrom(
						ShopNotFoundException.class)))
		;
	}

	@Test
	@DisplayName("점포 삭제 - 성공")
	public void 점포_삭제_성공() throws Exception {
		mockMvc.perform(delete(url+"/{shopId}", 1 ))
				.andDo(print())
				.andExpect(status().isNoContent())
		;
	}
}
