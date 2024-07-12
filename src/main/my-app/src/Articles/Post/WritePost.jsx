import React, {useEffect, useState} from 'react';
import {
    Box,
    Card,
    CardBody,
    Flex, Heading,
    HStack,
    IconButton,
    Input,
    SimpleGrid, Stack, StackDivider,
    Textarea,
    useToast,
    VStack, Text, CardHeader, TagLeftIcon, TagLabel, Tag, Button, Image, Skeleton
} from '@chakra-ui/react';
import axios from "axios";
import SubTemplate from "../../Templates/SubTemplate";
import ScrollToTop from "../../Atoms/ScrollToTop";
import {ArrowUpIcon, ChatIcon, EditIcon, Icon, ViewIcon} from "@chakra-ui/icons";
import {useNavigate} from "react-router-dom";
import {AiFillHeart} from "react-icons/ai";
import MDEditor from "@uiw/react-md-editor";
import serverUrl from "../../Constants/Constants";

const WritePost = () => {
    const id = window.location.pathname.split('/')[2];
    const [commentData, setCommentData] = useState({commentContent:'',commentNickname:'',commentPassword:'',post:''});
    const [writePost, setWritePost] = useState({id:1, thumbnail:`${serverUrl}:8080/white.jpg`,contentTitle:""});
    const [commentAll, setCommentAll] = useState([{commentContent:'',commentNickname:'',commentPassword:'',postId:id}]);
    const [isEditing, setIsEditing] = useState(false);
    const toast = useToast();
    const navigate = useNavigate();
    const [images, setImages] = useState([]);
    const [isLoaded, setIsLoaded]= useState(false);

    const CommentList = () => (
        <Card>
            <CardHeader pb={'0'}>
                <Heading size='sm'>Comments</Heading>
            </CardHeader>
            <CardBody>
                <Stack divider={<StackDivider />} spacing='2'>
                    {commentAll?.map((comment,index) =>
                        (<Flex direction={'column'} key={index}>
                            <Heading size='xs' textTransform='uppercase'>
                                {comment.memberName}
                            </Heading>
                            <Text pt='1' fontSize='xs'>
                                {comment.content}
                            </Text>
                        </Flex>)
                    )
                    }
                </Stack>
            </CardBody>
        </Card>
    );
    function handleInputChange(event) {
        const { name, value } = event.target;
        setCommentData({ ...commentData, [name]: value });
    }

    async function handleCommentSubmit(e) {
        e.preventDefault();
        console.log(commentData);
        try {
            axios
                .post(`${serverUrl}:8080/api/comment/add`, {
                    memberName: commentData.commentNickname,
                    memberPassword: commentData.commentPassword,
                    content: commentData.commentContent,
                    post: writePost,

                })
                .then(res => {
                    console.log("res:",res);
                    setCommentData({commentContent:'',commentNickname:'',commentPassword:'',post: writePost});
                    setCommentAll([...commentAll, res.data]);
                    if (res?.data) {
                        toast({
                            title: `댓글 업로드 완료됐나벼`,
                            status: 'success',
                            isClosable: true,
                        });
                    } else {
                        toast({
                            title: `잉ㅠ실패`,
                            status: 'error',
                            isClosable: true,
                        });
                    }
                });
        } catch (e) {
            console.error(e);
        }
    }

    async function handleLikes(e) {
        e.preventDefault();
        try {
            axios
                .get(`${serverUrl}:8080/api/post/like/${id}`)
                .then(res => {
                    console.log(res);
                    if (res?.data) {
                        console.log("좋아요 : ", res.data);
                        setWritePost({...writePost,likes : res.data});
                    } else {
                        toast({
                            title: `잉ㅠ좋아요 실패`,
                            status: 'error',
                            isClosable: true,
                        });
                    }
                });
        } catch (e) {
            console.error(e);
            toast({
                title: `잉ㅠ좋아요 실패`,
                status: 'error',
                isClosable: true,
            });
        }
    }


    useEffect(() => {
        const id = window.location.pathname.split('/')[2];

        (writePost.id<2)&&axios.get(`${serverUrl}:8080/api/post/${id}`).then(response => {
            setWritePost(response.data);
            setIsLoaded(true);
        }).catch(error=>console.log(error));
        axios.get(`${serverUrl}:8080/api/comment/all/${id}`).then(response => {
            setCommentAll(response.data);
        }).catch(error=>console.log(error));
        console.log(writePost.tag);
        (writePost.tag===2&&images.length<1)&&axios.get(`${serverUrl}:8080/api/image/all/${id}`).then(response => {
            console.log("image data:", response);
            setImages(response.data);
        }).catch(error=>console.log(error));
        console.log(commentAll);
        console.log(writePost);
    },[writePost]);

    return (
            <Skeleton isLoaded={isLoaded} fadeDuration={1}>
        <SubTemplate pageTitle={writePost.contentTitle} titleQuery={writePost.contentTitle}>
            <Flex justify={"flex-end"}>
                <IconButton icon={<EditIcon />} aria-label='editPost' onClick={()=>navigate(`/update/${id}`)}/>
            </Flex>
            <ScrollToTop/>
            <HStack justify={"space-between"}>
                <Flex gap={3}>
                    <Tag size={'md'} key={1} variant='subtle' colorScheme='gray'>
                        <TagLeftIcon boxSize='12px' as={ViewIcon} />
                        <TagLabel>{writePost.views}</TagLabel>
                    </Tag>
                    <Tag size={'md'} key={2} variant='subtle' colorScheme='cyan'>
                        <TagLeftIcon boxSize='12px' as={ChatIcon} />
                        <TagLabel>{commentAll.length}</TagLabel>
                    </Tag>
                    <Tag size={'md'} key={3} variant='subtle' colorScheme='pink'>
                        <TagLeftIcon boxSiz e='12px' as={AiFillHeart} />
                        <TagLabel>{writePost.likes}</TagLabel>
                    </Tag>
                </Flex>
                <Flex justify={"flex-end"}><Button type={'submit'} onClick={handleLikes} aria-label={'likes'} colorScheme={"pink"}><AiFillHeart/></Button></Flex>
            </HStack>
            <Card>
                <CardBody>
                    <MDEditor.Markdown source={writePost.content} style={{ whiteSpace: 'pre-wrap',backgroundColor:'white',color:'black'}} />
                    {images.map((image,index) => (
                        <Image key={index} src={image.url} alt={image.id} />
                        ))}
                </CardBody>
            </Card>
            <VStack height={'100vh'} style={{ flexDirection: 'column' }}>
                <VStack width={'75vw'} p={'5'} alignItems={'stretch'}>

                    <CommentList />
                    <Flex>
                        <Input name="commentNickname" placeholder={'귀여운 닉네임'} value={commentData&&commentData.commentNickname} onChange={handleInputChange}/>
                        <Input name="commentPassword" placeholder={'비밀번호 486'} value={commentData&&commentData.commentPassword} onChange={handleInputChange}/>
                    </Flex>
                    <Flex >
                        <Textarea  name="commentContent" placeholder={'댓글을 입력해주세요.'} value={commentData&&commentData.commentContent} onChange={handleInputChange}/>
                        <IconButton icon={<ArrowUpIcon/>} type={'submit'} onClick={handleCommentSubmit}  aria-label={'commentSubmit'}/>
                    </Flex>
                </VStack>
            </VStack>
        </SubTemplate>
            </Skeleton>
    );
};

export default WritePost;