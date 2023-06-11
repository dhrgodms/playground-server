import React, {useCallback, useRef, useState} from 'react';
import {
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
    Button, useToast, Flex, Box, Heading, Textarea,
} from '@chakra-ui/react';
import axios from "axios";
import LoginForm from "../Articles/LoginForm";
import MainTemplate from "../Templates/MainTemplate";
import SubTemplate from "../Templates/SubTemplate";
import {ArrowUpIcon} from "@chakra-ui/icons";
function UploadForm() {
        const toast = useToast();
        const [formData, setFormData] = useState({
            user_id:1,
            content_title: "",
            content: "내용!",
            thumbnail: "",
        });

        function handleInputChange(event) {
            const { name, value } = event.target;
            setFormData({ ...formData, [name]: value });
        }

        const SettingUserThumbnail = () => {
            const inputRef = useRef(null);
            const onUploadImage = useCallback(e => {
                if (!e.target.files) {
                    return;
                }
                console.log(e.target.files[0]);

                const formImageData = new FormData();
                formImageData.append('file',e.target.files[0]);
                console.log(formImageData);

                axios.post("http://localhost:8080/api/post/thumbnail-upload",formImageData,{
                    'Content-Type': 'multipart/form-data',
                },)
                    .then(response => {
                        console.log(response);
                        setFormData({ ...formData, thumbnail: response.data });
                        console.log(formData.thumbnail);

                    })
                    .catch(error => {
                        console.error(error);
                    });
            }, []);

            const onUploadImageButtonClick = useCallback(() => {
                if(!inputRef.current){
                    return;
                }
                inputRef.current.click();
            },[]);

            // const onDeleteImage = useCallback(e => {
            //     if (!e.target.files) {
            //         return;
            //     }
            //
            //     const formData = new FormData();
            //     formData.delete(inputRef.current.files[0]);
            //
            //     axios.post(`http://localhost:8080/api/post/delete`,formData,{
            //         'Content-Type': 'multipart/form-data',
            //     },)
            //         .then(response => {
            //             console.log(response);
            //         })
            //         .catch(error => {
            //             console.error(error);
            //         });
            // }, []);


            return(
                <FormControl>
                    <Flex gap={'3'} align={'center'}>
                    <Input
                        type="file"
                        onChange={onUploadImage}
                        accept='image/*'
                        ref={inputRef}
                        style={{display:"none"}}
                        name="thumbnail"
                    />
                    <Button size={'sm'} label="이미지업로드" onClick={onUploadImageButtonClick}>+Thumbnail</Button>
                    <Input
                        focusBorderColor="green"
                        size={'sm'}
                        colorScheme={'green'}
                        varient="filled"
                        isReadOnly={true}
                        value={formData.thumbnail}
                    />
                    </Flex>
                    {/*<Button label="이미지 제거" onClick={onDeleteImage} />*/}
                </FormControl>
            )
        }
        async function handleSubmit(e) {
            e.preventDefault();
            console.log(formData);
            try {
                axios
                    .post('http://localhost:8080/api/post/add', {
                        userId:formData.user_id,
                        contentTitle: formData.content_title,
                        content: formData.content,
                        thumbnail: formData.thumbnail,
                    })
                    .then(res => {
                        console.log("res:",res);
                        setFormData({user_id:"",
                            content_title: "",
                            content: "",
                            thumbnail: ""});
                        if (res?.data) {
                            toast({
                                title: `업로드 됐나바`,
                                status: 'success',
                                isClosable: true,
                            });
                        } else {
                            toast({
                                title: `업실`,
                                status: 'error',
                                isClosable: true,
                            });
                        }
                    });
            } catch (e) {
                console.error(e);
            }
        }

        return (
            <SubTemplate titleQuery={"uploadform"} pageTitle={"uploadform"}>
                <Box my={4} textAlign="left">
                    <form
                        onSubmit={handleSubmit}
                        style={{ flexDirection: 'column', gap: '10px' }}
                    >
                        <Flex direction={'column'} justify={'center'} gap={'5'}>

                        <FormControl isRequired>
                            <FormLabel>Content Title</FormLabel>
                            <Input
                                focusBorderColor={'green'}
                                placeholder="..OㅅO.."
                                onChange={handleInputChange}
                                name="content_title"
                                value={formData && formData.content_title}
                            />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>Content</FormLabel>
                            <Textarea
                                focusBorderColor="green"
                                type="text"
                                placeholder="..OㅅO.."
                                onChange={handleInputChange}
                                name="content"
                                value={formData && formData.content}
                            />
                        </FormControl>
                        <SettingUserThumbnail />

                        </Flex>
                        <Flex
                            style={{
                                flexDirection: 'column',
                                marginTop: '20px',
                                gap: '10px',
                            }}
                        >
                            <Button type="sumbit" colorScheme="yellow">
                                upload <ArrowUpIcon />
                            </Button>
                        </Flex>
                    </form>
                </Box>
            </SubTemplate>
        );
}

const UploadPostPage = () => {
    return(<Flex justify={'center'}>
        <Box>
            <UploadForm />
        </Box>
    </Flex>);
}
export default UploadPostPage;