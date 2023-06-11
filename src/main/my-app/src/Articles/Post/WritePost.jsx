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
    VStack, Text, CardHeader
} from '@chakra-ui/react';
import axios from "axios";
import SubTemplate from "../../Templates/SubTemplate";
import ScrollToTop from "../../Atoms/ScrollToTop";
import {ArrowUpIcon, EditIcon, Icon} from "@chakra-ui/icons";
import EditableTextArea from "../../Atoms/EditableTextArea";
import {useNavigate} from "react-router-dom";

const WritePost = () => {
    const id = window.location.pathname.split('/')[2];
    const [commentData, setCommentData] = useState({commentContent:'',commentNickname:'',commentPassword:'',postId:id});
    const [writePost, setWritePost] = useState({id:1, thumbnail:'http://localhost:8080/white.jpg',contentTitle:""});
    const [commentAll, setCommentAll] = useState([{commentContent:'',commentNickname:'',commentPassword:'',postId:id}]);
    const [isEditing, setIsEditing] = useState(false);
    const toast = useToast();
    const navigate = useNavigate();

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
                .post('http://localhost:8080/api/comment/add', {
                    memberName: commentData.commentNickname,
                    memberPassword: commentData.commentPassword,
                    content: commentData.commentContent,
                    postId: commentData.postId,
                })
                .then(res => {
                    console.log("res:",res);
                    setCommentData({commentContent:'',commentNickname:'',commentPassword:'',postId:id});
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


    useEffect(() => {
        const id = window.location.pathname.split('/')[2];

        (writePost.id<2)&&axios.get(`http://localhost:8080/api/post/${id}`).then(response => setWritePost(response.data)).catch(error=>console.log(error));
        axios.get(`http://localhost:8080/api/comment/all/${id}`).then(response => {
            setCommentAll(response.data);
        }).catch(error=>console.log(error));
        console.log(commentAll);
    },[writePost]);

    return (
        <SubTemplate pageTitle={writePost.contentTitle} titleQuery={writePost.contentTitle}>
            <ScrollToTop/>
            <Card>
                <CardBody>
                    {writePost.content}
                </CardBody>
            </Card>
            <IconButton icon={<EditIcon />} aria-label='editPost' onClick={()=>navigate(`/update/${id}`)}/>
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
    );
};

export default WritePost;