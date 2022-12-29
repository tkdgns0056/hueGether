package com.project.promotion.controller;

import com.project.promotion.model.member.LoginUserDto;
import com.project.promotion.model.member.Member;
import com.project.promotion.model.member.UserDto;
import com.project.promotion.service.auth.PrincipalDetailsService;
import com.project.promotion.service.mail.MailService;
import com.project.promotion.service.member.UserService;
import com.project.promotion.validator.CheckMemberIdValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    //중복 체크 유효성 검사
    private final CheckMemberIdValidator checkMemberIdValidator;

    @GetMapping("/save")
    public String saveForm(@ModelAttribute("userDto") UserDto userDto){

        return "join";
    }

    /* 시큐리티 이용한 회원가입 */
    @PostMapping("/save")
    public String saveUser(@Valid UserDto userDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) {
            //회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("userDto", userDto);
            //회원 가입 페이지로 다시 리턴
            return "join";
        }
            //Member에 userDto 넣음.
            Member member = userDto.toEntity();
            userService.checkLoginInsert(member);
            System.out.println("암호화된 비밀번호 = " + member.getPassword());
            return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model){

        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "loginForm";
    }

    /* 로그 아웃 */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //세션 삭제 한다.
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    /**
     * 이메일 중복 체크
     * @param email
     * @return
     */
    @PostMapping("/save/id-exists")
    @ResponseBody
    public boolean checkUserIdDuplicate(@RequestParam("email") String email, @RequestParam("loginType") String loginType){

        boolean idChkResult = userService.isUserIdExist(email, loginType);

        return idChkResult;
    }


    /*이메일 인증 전송*/
    @PostMapping("/emailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam("email") String email) throws Exception {

//        Gson gson = new Gson();
        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증 코드 : " + code);
        return code;
    }

    /* 백엔드에서 검증 처리 해줘야할 듯 */
    @InitBinder
    public void initBinder(WebDataBinder binder){

        binder.addValidators(checkMemberIdValidator);
    }

}
