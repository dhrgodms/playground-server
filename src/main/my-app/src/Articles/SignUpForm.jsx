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

const SignUpForm = () => {

    const toast = useToast();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        userEmail: 'okhe@gmail.com',
        userBirth: '001200',
        userNickname: '핸니',
        admin: 'FALSE',
    });
    const [user, setUser] = useState({});
    const [show, setShow] = React.useState(false);
    const handleClick = () => setShow(!show);

    function handleInputChange(event) {
        const { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }
    // User(userSequenceId=1, userEmail=aabbcc@gmail.com, userBirth=001200, userNickname=침착맨, admin=FALSE, roles=[ROLE_USER])
    async function handleSubmit(e) {
        e.preventDefault();
        console.log(formData);
        try {
            axios
                .post('http://localhost:8080/join', {
                    userEmail: formData.userEmail,
                    userBirth: formData.userBirth,
                    userNickname: formData.userNickname,
                    admin: formData.admin,
                })
                .then(res => {
                    console.log("res:",res);
                        // if (res?.data.loggedIn) {
                        // 세션 정보가 있다면 loginState를 true로 변경
                        // setUser(res.data.user);
                        console.log(user);
                        toast({
                            title: `'${formData?.userNickname}' 님 회원가입 완료 🎉`,
                            status: 'success',
                            isClosable: true,
                        });
                        // setCookie('user', res.data.user, {
                        //     expires: new Date(Date.now() + 1000 * 60 * 5),
                        // });
                        navigate('/', { replace: true });
                        // }
                });
        } catch (e) {
            console.error(e);
        }
    }

    return (
        <Flex align="center" justifyContent="center">
            <Box p={2}>
                <Box textAlign="center">
                    <Heading style={{ marginBottom: '40px' }}>Sign Up</Heading>
                </Box>
                <Box my={4} textAlign="left">
                    <form
                        onSubmit={handleSubmit}
                        style={{ flexDirection: 'column', gap: '10px' }}
                    >
                        <FormControl isRequired>
                            <FormLabel>Email</FormLabel>
                            <Input
                                type="text"
                                onChange={handleInputChange}
                                name="userEmail"
                                value={formData && formData.userEmail}
                            />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>Birthdate</FormLabel>
                            <InputGroup size="md">
                                <Input
                                    type="text"
                                    onChange={handleInputChange}
                                    name="userBirth"
                                    value={formData && formData.userBirth}
                                />
                                <InputRightElement width="4.5rem">
                                    <Button h="1.75rem" size="sm" onClick={handleClick}>
                                        {show ? 'Hide' : 'Show'}
                                    </Button>
                                </InputRightElement>
                            </InputGroup>
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>Nickname</FormLabel>
                            <InputGroup size="md">
                                <Input
                                    type="text"
                                    onChange={handleInputChange}
                                    name="userNickname"
                                    value={formData && formData.userNickname}
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
                                Submit
                            </Button>
                            <Button type="button" onClick={() => navigate('/login')}>
                                Login
                            </Button>
                        </Flex>
                    </form>
                </Box>
            </Box>
        </Flex>
    );
};

export default SignUpForm;