import React, { useState, useEffect } from 'react';
import {
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
    Button,
    Flex,
    Box,
    Heading, useToast,
} from '@chakra-ui/react';
import {useNavigate} from "react-router-dom";
import axios from "axios";

const LoginForm = () => {

    const toast = useToast();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        id: '..ㅇㅅㅇ..',
        pw: '12345',
    });
    const [user, setUser] = useState({});
    const [show, setShow] = React.useState(false);
    const handleClick = () => setShow(!show);

    function handleInputChange(event) {
        const { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }
    async function handleSubmit(e) {
        e.preventDefault();
        console.log(formData);
        try {
            axios
                .post('http://localhost:8080/auth/login', {
                    id: formData.id,
                    pw: formData.pw,
                })
                .then(res => {
                    console.log("res:",res);
                    if (res?.data.message === 'user does not exist') {
                        toast({
                            title: `회원정보가 존재하지 않습니다.`,
                            status: 'error',
                            isClosable: true,
                        });
                    } else if (res?.data.message === 'error, id or password incorrect') {
                        toast({
                            title: `회원정보가 일치하지 않습니다.`,
                            status: 'error',
                            isClosable: true,
                        });
                    } else {
                        // if (res?.data.loggedIn) {
                            // 세션 정보가 있다면 loginState를 true로 변경
                            // setUser(res.data.user);
                            console.log(user);
                            toast({
                                title: `${res.data.user.userName}님 환영합니다.`,
                                status: 'success',
                                isClosable: true,
                            });
                            // setCookie('user', res.data.user, {
                            //     expires: new Date(Date.now() + 1000 * 60 * 5),
                            // });
                            navigate('/', { replace: true });
                        // }
                    }
                });
        } catch (e) {
            console.error(e);
        }
    }

    return (
        <Flex align="center" justifyContent="center">
            <Box p={2}>
                <Box textAlign="center">
                    <Heading style={{ marginBottom: '40px' }}>LOG IN</Heading>
                </Box>
                <Box my={4} textAlign="left">
                    <form
                        onSubmit={handleSubmit}
                        style={{ flexDirection: 'column', gap: '10px' }}
                    >
                        <FormControl isRequired>
                            <FormLabel>예쁜 당신의 이름</FormLabel>
                            <Input
                                type="text"
                                placeholder="..OㅅO.."
                                onChange={handleInputChange}
                                name="id"
                                value={formData && formData.id}
                            />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>비밀번호486</FormLabel>
                            <InputGroup size="md">
                                <Input
                                    pr="4.5rem"
                                    type={show ? 'text' : 'password'}
                                    placeholder="Enter password"
                                    onChange={handleInputChange}
                                    name={'pw'}
                                />
                                <InputRightElement width="4.5rem">
                                    <Button h="1.75rem" size="sm" onClick={handleClick}>
                                        {show ? 'Hide' : 'Show'}
                                    </Button>
                                </InputRightElement>
                            </InputGroup>
                        </FormControl>
                        <Flex
                            style={{
                                flexDirection: 'column',
                                marginTop: '20px',
                                gap: '10px',
                            }}
                        >
                            <Button type="sumbit" colorScheme="pink">
                                Login
                            </Button>
                            <Button type="button" onClick={() => navigate('/signup')}>
                                Sign Up
                            </Button>
                        </Flex>
                    </form>
                </Box>
            </Box>
        </Flex>
    );
};

export default LoginForm;