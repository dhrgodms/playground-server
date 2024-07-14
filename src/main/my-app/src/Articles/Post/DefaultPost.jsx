import React, { useEffect, useState } from "react";
import {
  Card,
  CardBody,
  Flex,
  HStack,
  IconButton,
  useToast,
  TagLeftIcon,
  TagLabel,
  Tag,
  Button,
  Image,
  Skeleton,
} from "@chakra-ui/react";
import axios from "axios";
import SubTemplate from "../../Templates/SubTemplate";
import ScrollToTop from "../../Atoms/ScrollToTop";
import { ChatIcon, EditIcon, ViewIcon } from "@chakra-ui/icons";
import { useNavigate } from "react-router-dom";
import { AiFillHeart } from "react-icons/ai";
import MDEditor from "@uiw/react-md-editor";
import serverUrl from "../../Constants/Constants";
import CommentContainer from "../../Components/Comments";

const DefaultPost = () => {
  const id = window.location.pathname.split("/")[2];
  const [writePost, setWritePost] = useState({
    id: 1,
    thumbnail: `${serverUrl}:8080/white.jpg`,
    contentTitle: "",
  });
  const [commentAll, setCommentAll] = useState([
    {
      commentContent: "",
      commentNickname: "",
      commentPassword: "",
      postId: id,
    },
  ]);
  const toast = useToast();
  const navigate = useNavigate();
  const [images, setImages] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);

  async function handleLikes(e) {
    e.preventDefault();
    try {
      axios.get(`${serverUrl}:8080/api/post/like/${id}`).then((res) => {
        if (res?.data) {
          setWritePost({ ...writePost, likes: res.data });
        } else {
          toast({
            title: `잉ㅠ좋아요 실패`,
            status: "error",
            isClosable: true,
          });
        }
      });
    } catch (e) {
      console.error(e);
      toast({
        title: `잉ㅠ좋아요 실패`,
        status: "error",
        isClosable: true,
      });
    }
  }

  useEffect(() => {
    // postId 추출
    const id = window.location.pathname.split("/")[2];

    // id<2 (기본 Post)
    writePost.id < 2 &&
      axios
        .get(`${serverUrl}:8080/api/post/${id}`)
        .then((response) => {
          setWritePost(response.data);
          // 스켈레톤 생성을 위한 load state update
          setIsLoaded(true);
        })
        .catch((error) => console.log(error));

    // postId에 따른 댓글 가져오기
    axios
      .get(`${serverUrl}:8080/api/comment/all/${id}`)
      .then((response) => {
        setCommentAll(response.data);
      })
      .catch((error) => console.log(error));

    // tag가 2이면
    writePost.tag === 2 &&
      images.length < 1 &&
      axios
        .get(`${serverUrl}:8080/api/image/all/${id}`)
        .then((response) => {
          setImages(response.data);
        })
        .catch((error) => console.log(error));
  });

  return (
    <Skeleton isLoaded={isLoaded} fadeDuration={1}>
      <SubTemplate
        pageTitle={writePost.contentTitle}
        titleQuery={writePost.contentTitle}
      >
        <Flex justify={"flex-end"}>
          <IconButton
            icon={<EditIcon />}
            aria-label="editPost"
            onClick={() => navigate(`/update/${id}`)}
          />
        </Flex>
        <ScrollToTop />
        <HStack justify={"space-between"}>
          <Flex gap={3}>
            <Tag size={"md"} key={1} variant="subtle" colorScheme="gray">
              <TagLeftIcon boxSize="12px" as={ViewIcon} />
              <TagLabel>{writePost.views}</TagLabel>
            </Tag>
            <Tag size={"md"} key={2} variant="subtle" colorScheme="cyan">
              <TagLeftIcon boxSize="12px" as={ChatIcon} />
              <TagLabel>{commentAll.length}</TagLabel>
            </Tag>
            <Tag size={"md"} key={3} variant="subtle" colorScheme="pink">
              <TagLeftIcon boxSiz e="12px" as={AiFillHeart} />
              <TagLabel>{writePost.likes}</TagLabel>
            </Tag>
          </Flex>
          <Flex justify={"flex-end"}>
            <Button
              type={"submit"}
              onClick={handleLikes}
              aria-label={"likes"}
              colorScheme={"pink"}
            >
              <AiFillHeart />
            </Button>
          </Flex>
        </HStack>
        <Card>
          <CardBody>
            <MDEditor.Markdown
              source={writePost.content}
              style={{
                whiteSpace: "pre-wrap",
                backgroundColor: "white",
                color: "black",
              }}
            />
            {images.map((image, index) => (
              <Image key={index} src={image.url} alt={image.id} />
            ))}
          </CardBody>
        </Card>
        <CommentContainer
          id={id}
          commentAll={commentAll}
          setCommentAll={setCommentAll}
          writePost={writePost}
        />
      </SubTemplate>
    </Skeleton>
  );
};

export default DefaultPost;
