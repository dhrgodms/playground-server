import React, { useEffect, useState } from "react";
import {
  Button,
  Card,
  CardBody,
  Flex,
  HStack,
  IconButton,
  Image,
  Skeleton,
  Tag,
  TagLabel,
  TagLeftIcon,
  useToast,
} from "@chakra-ui/react";
import axios from "axios";
import SubTemplate from "../../Templates/SubTemplate";
import ScrollToTop from "../../Atoms/ScrollToTop";
import { ChatIcon, EditIcon, ViewIcon } from "@chakra-ui/icons";
import { useNavigate, useParams } from "react-router-dom";
import { AiFillHeart } from "react-icons/ai";
import MDEditor from "@uiw/react-md-editor";
import { serverUrl, serverUrlV2 } from "../../Constants/Constants";
import CommentContainer from "../../Components/Comments";

const DefaultPost = () => {
  const { id } = useParams();
  const [writePost, setWritePost] = useState({
    id: 1,
    thumbnail: `${serverUrl}/white.jpg`,
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
  const [files, setFiles] = useState([]);
  const [markdown, setMarkdown] = useState([]);
  const [isFetching, setIsFetching] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false);

  async function handleLikes(e) {
    e.preventDefault();
    try {
      axios.post(`${serverUrlV2}/posts/${id}/like`).then((res) => {
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

  // 게시글 정보, 댓글 가져오기
  useEffect(() => {
    axios
      .get(`${serverUrlV2}/posts/${id}`)
      .then((response) => {
        setWritePost(response.data);
        setIsLoaded(true);
      })
      .catch((error) => console.log(error));

    axios
      .get(`${serverUrl}:8080/api/comment/all/${id}`)
      .then((response) => {
        setCommentAll(response.data);
      })
      .catch((error) => console.log(error));
  }, [id]);

  // tag가 2(만화글)일 때 이미지 가져오기
  useEffect(() => {
    if (writePost.tag === 2) {
      axios
        .get(`${serverUrl}:8080/api/image/all/${id}`)
        .then((response) => {
          setImages(response.data);
        })
        .catch((error) => console.log(error));
    }
  }, [id, writePost.tag]);

  // tag가 4(마크다운글)일 때 파일 및 마크다운 내용 가져오기
  useEffect(() => {
    if (writePost.tag === 4) {
      const fetchData = async () => {
        try {
          const response = await axios.get(
            `${serverUrl}:8080/api/files/all/${id}`
          );
          setFiles(response.data);

          // 여러 파일이 있을 경우, 첫 번째 파일만 마크다운으로 표시 (필요시 로직 수정)
          if (response.data.length > 0) {
            const file = response.data[0];
            const text = await fetch(`${serverUrl}${file.filePath}`).then((res) => res.text());
            setMarkdown(text);
          }
        } catch (error) {
          console.error(error);
        } finally {
          setIsFetching(false);
        }
      };
      if (!isFetching) {
        setIsFetching(true);
        fetchData();
      }
    }
  }, [id, writePost.tag]);

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
            onClick={() => navigate(`/post/update/${id}`)}
          />
        </Flex>
        <ScrollToTop />
        <HStack justify={"space-between"}>
          <Flex gap={3}>
            <Tag size={"md"} key={1} variant="subtle" colorScheme="gray">
              <TagLeftIcon boxsiz="12px" as={ViewIcon} />
              <TagLabel>{writePost.views}</TagLabel>
            </Tag>
            <Tag size={"md"} key={2} variant="subtle" colorScheme="cyan">
              <TagLeftIcon boxsiz="12px" as={ChatIcon} />
              <TagLabel>{commentAll.length}</TagLabel>
            </Tag>
            <Tag size={"md"} key={3} variant="subtle" colorScheme="pink">
              <TagLeftIcon boxsiz="12px" as={AiFillHeart} />
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
            {writePost.tag === 4 ? (
              <MDEditor.Markdown
                source={markdown}
                style={{
                  whiteSpace: "pre-wrap",
                  backgroundColor: "white",
                  color: "black",
                }}
              />
            ) : (
              <div
                style={{
                  whiteSpace: "pre-wrap",
                  backgroundColor: "white",
                  color: "black",
                }}
              >
                {writePost.content}
              </div>
            )}
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
