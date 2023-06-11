import React, {useCallback, useEffect, useRef, useState} from 'react';
import {
    Box,
    Flex,
    Input,
    Textarea,
    useToast, FormControl, Button, FormLabel, IconButton
} from '@chakra-ui/react';
import axios from "axios";
import {ArrowUpIcon, DeleteIcon} from "@chakra-ui/icons";
import {useNavigate} from "react-router-dom";
import SubTemplate from "../Templates/SubTemplate";
import DeleteAlertDialog from "../Atoms/DeleteAlertDialog";

function UpdatePostPage(){
    const id = window.location.pathname.split('/')[2];
    const [writePost, setWritePost] = useState({id:1, thumbnail:'http://localhost:8080/white.jpg',contentTitle:""});
    const toast = useToast();

    const [formData, setFormData] = useState({
        user_id:1,
        content_title: writePost.contentTitle,
        content: writePost.content,
        thumbnail: writePost.thumbnail,
    });
    const navigate = useNavigate();

    useEffect(() => {
        const id = window.location.pathname.split('/')[2];
        (writePost.id<2)&&axios.get(`http://localhost:8080/api/post/${id}`).then(response => {
            setWritePost(response.data);
        }).catch(error=>console.log(error));

        setFormData({user_id:1,
            content_title: writePost.contentTitle,
            content: writePost.content,
            thumbnail: writePost.thumbnail,});
        },[writePost]);
    function onDeletePost(){

        axios.delete(`http://localhost:8080/api/post/delete/${id}`).then(response => {
            console.log(response);
            toast({
                title: "게시글 삭제 완료",
                status: "success",
                duration: 9000,
                isClosable: true,
            });
            navigate('/writes');
        }).catch(error=>console.log(error));
    }
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
                .post('http://localhost:8080/api/post/update', {
                    id:id,
                    userId:formData.user_id,
                    contentTitle: formData.content_title,
                    content: formData.content,
                    thumbnail: formData.thumbnail,
                })
                .then(res => {
                    console.log("res:",res);
                    setFormData({
                        user_id:"",
                        content_title: "",
                        content: "",
                        thumbnail: ""});
                    if (res?.data) {
                        toast({
                            title: `수정, 성공적.`,
                            status: 'success',
                            isClosable: true,
                        });
                        navigate(`/post/${id}`);
                    } else {
                        toast({
                            title: `수정 실패`,
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
        <SubTemplate pageTitle={writePost.contentTitle+"   (EDITING)"} titleQuery={writePost.contentTitle}>
            <Box my={4} textAlign="left">
                <form
                    onSubmit={handleSubmit}
                    style={{ flexDirection: 'column', gap: '10px' }}
                >
                    <Flex direction={'column'} justify={'center'} gap={'5'}>

                        <FormControl isRequired>
                            <FormLabel>Content Title</FormLabel>
                            <Textarea
                                focusBorderColor="green"
                                type="text"
                                defaultValue={formData && formData.content_title}
                                onChange={handleInputChange}
                                name="content_title"
                            />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel>Content</FormLabel>
                            <Textarea
                                focusBorderColor="green"
                                type="text"
                                defaultValue={formData && formData.content}
                                onChange={handleInputChange}
                                name="content"
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
                        <Button type="sumbit" colorScheme="yellow" variant={'outline'}>
                            SAVE <ArrowUpIcon />
                        </Button>
                        <IconButton icon={<DeleteIcon/>} colorScheme="blackAlpha" onClick={onDeletePost}  aria-label={'delete'}/>

                    </Flex>
                </form>
            </Box>
        </SubTemplate>
    );
}

export default UpdatePostPage;